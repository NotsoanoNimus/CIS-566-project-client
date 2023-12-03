package xyz.xmit.silverclient.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.xmit.silverclient.api.request.*;
import xyz.xmit.silverclient.models.BaseModel;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;


/**
 * DESIGN PATTERN: Singleton (Creational)
 * <br /><br />
 * The central HTTP API client for the application, acting as a bridge between the
 * "outside world" and this local program. The Singleton pattern is an absolute
 * necessity for the API client for multiple reasons:
 * <ul>
 *     <li>There is no reason to use multiple simultaneous connections to push/pull data.</li>
 *     <li>Having a single class instantiated allows control over client rate limiting.</li>
 *     <li>Authentication parameters can be stored in a single place and remain there
 *     for the lifetime of the application.</li>
 * </ul>
 * In this case, we use the "classic" Singleton pattern of defining a private, static member
 * with a public accessor method for that instance. Elsewhere in the application (such as the
 * API Facade class), you will see this instance being used directly with
 * <em>HttpApiClient.getInstance()</em>.
 */
public final class HttpApiClient
{
    private static HttpApiClient instance = new HttpApiClient();

    private ApiAuthenticationContext authenticationContext;

    private HttpApiClient() {
        try {
            // Always trust remote certificates, and do not validate hosts which do not match either
            //   a local IPv4 address or 'localhost'. This enables testing on insecure local server instances.
            TrustManager trm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            };

            var sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { trm }, null);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(CustomHostnameVerification.instance);
        } catch (Exception ex) {
            System.out.println("Failed to initialize default SSLSocketFactory");
            ex.printStackTrace();
        }
    }

    public static HttpApiClient getInstance()
    {
        return HttpApiClient.instance == null
                ? (HttpApiClient.instance = new HttpApiClient())
                : HttpApiClient.instance;
    }

    public synchronized void setAuthenticationContext(ApiAuthenticationContext authenticationContext)
    {
        this.authenticationContext = authenticationContext;
    }

    public ApiAuthenticationContext getAuthenticationContext()
    {
        return authenticationContext;
    }

    /**
     * Contacts the remote API with the constructed HTTP/S connection object and attempts to complete a
     * transaction. Will attempt to deserialize the API's response into the given class. If that is not
     * possible, this method will attempt to send back a wrapped API response message with failure info.
     *
     * @param connection The connection to utilize for streaming the request/response data to/from the API.
     * @param dummyModelClass The expected model class into which data should be deserialized.
     * @return The deserialized API response as a wrapped response object.
     * @param <T> The class type into which the response data should be deserialized.
     * @throws Exception If there is a problem contacting the API or understanding the response data.
     */
    public <T> WrappedApiResponse<T> readWrappedApiResponseOrDie(HttpsURLConnection connection, Class<T> dummyModelClass)
            throws Exception
    {
        System.out.println("Outgoing Connection: " + connection.getRequestMethod() + " " + connection.getURL());
        System.out.println("-----");

        connection.connect();

        var responseCode = connection.getResponseCode();
        var responseMessage = connection.getResponseMessage();

        var response = responseCode < HttpURLConnection.HTTP_BAD_REQUEST
            ? new String(connection.getInputStream().readAllBytes())
            : new String(connection.getErrorStream().readAllBytes());

        System.out.println("Connection Response: " + responseCode + " - " + responseMessage);
        System.out.println(response + "\n-----");

        // Need to be able to fall back to an acceptable Wrapper API response in the event
        //   that a server error does not come back in the deserializable JSON format we expect.
        return responseCode < HttpURLConnection.HTTP_BAD_REQUEST
            ? new WrappedApiResponse<T>(response, dummyModelClass)
            : new WrappedApiResponse<T>(response, dummyModelClass, false, responseMessage);
    }

    /**
     * Raw HTTP request using an already-created request object and the current authentication context.
     */
    public <X extends BaseApiRequest, Y> WrappedApiResponse<Y> RawHttpRequest(
            X request,
            Class<Y> blankModelClass
    ) throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(request, this.authenticationContext);

        connection.connect();

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }
    /**
     * Raw GET request using a model ID and the current authentication context.
     */
    public <TId, Y extends BaseModel<TId>> WrappedApiResponse<Y> RawGetRequest(TId objectId, Class<Y> blankModelClass)
            throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(
                new GenericGetRequest<>(objectId, blankModelClass),
                this.authenticationContext);

        connection.connect();

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }

    /**
     * Raw DELETE request using an existing model and the current authentication context.
     */
    public <Y extends BaseModel<?>> WrappedApiResponse<Y> RawDeleteRequest(Y model, Class<Y> blankModelClass)
            throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(
                new GenericDeleteRequest<>(model, blankModelClass),
                this.authenticationContext);

        connection.connect();

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }

    /**
     * Raw PUT request using an existing model and the current authentication context.
     */
    public <Y extends BaseModel<?>> WrappedApiResponse<Y> RawPutRequest(Y model, Class<Y> blankModelClass)
            throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(
                new GenericPutRequest<>(model, blankModelClass),
                this.authenticationContext);

        connection.setDoOutput(true);
        connection.connect();

        try (var outputStream = connection.getOutputStream()) {
            var jsonInputString = new ObjectMapper().writeValueAsString(model);

            var input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }

    /**
     * Raw POST request using an existing model and the current authentication context.
     */
    public <Y extends BaseModel<?>> WrappedApiResponse<Y> RawPostRequest(Y model, Class<Y> blankModelClass)
            throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(
                new GenericPostRequest<>(model, blankModelClass),
                this.authenticationContext);

        connection.setDoOutput(true);
        connection.connect();

        try (var outputStream = connection.getOutputStream()) {
            var jsonInputString = new ObjectMapper().writeValueAsString(model);

            var input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }
}
