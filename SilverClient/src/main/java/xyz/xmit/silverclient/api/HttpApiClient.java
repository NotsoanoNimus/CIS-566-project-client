package xyz.xmit.silverclient.api;

import xyz.xmit.silverclient.api.request.*;
import xyz.xmit.silverclient.models.BaseModel;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.cert.X509Certificate;


public final class HttpApiClient
{
    private static HttpApiClient instance = new HttpApiClient();

    private ApiAuthenticationContext authenticationContext;

    private HttpApiClient() {
        try {
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

    public synchronized void setAuthenticationContext(ApiAuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

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

    public <X extends BaseApiRequest, Y> WrappedApiResponse<Y> GetAsync(
            X request,
            Class<Y> blankModelClass
    ) throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(request, this.authenticationContext);

        connection.connect();

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }

    public <TId, Y extends BaseModel<TId>> WrappedApiResponse<Y> GetAsync(TId objectId, Class<Y> blankModelClass)
            throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(
                new GenericGetRequest<>(objectId, blankModelClass),
                this.authenticationContext);

        connection.connect();

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }

    public <Y extends BaseModel<?>> WrappedApiResponse<Y> DeleteAsync(Y model, Class<Y> blankModelClass)
            throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(
                new GenericDeleteRequest<>(model, blankModelClass),
                this.authenticationContext);

        connection.connect();

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }

    public <Y extends BaseModel<?>> WrappedApiResponse<Y> PutAsync(Y model, Class<Y> blankModelClass)
            throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(
                new GenericPutRequest<>(model, blankModelClass),
                this.authenticationContext);

        connection.connect();

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }

    public <Y extends BaseModel<?>> WrappedApiResponse<Y> PostAsync(Y model, Class<Y> blankModelClass)
            throws Exception
    {
        var connection = HttpConnectionFactory.CreateSecure(
                new GenericPostRequest<>(model, blankModelClass),
                this.authenticationContext);

        connection.connect();

        return this.<Y>readWrappedApiResponseOrDie(connection, blankModelClass);
    }
}
