package xyz.xmit.silverclient.api;

import xyz.xmit.silverclient.api.request.BaseApiRequest;
import xyz.xmit.silverclient.api.request.GenericGetRequest;

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
    private static HttpApiClient instance;

    private ApiAuthenticationContext authenticationContext;

    private HttpApiClient() {
        try {
            TrustManager trm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {

                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
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

    private WrappedApiResponse readWrappedApiResponseOrDie(HttpsURLConnection connection)
            throws Exception
    {
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
            ? new WrappedApiResponse(response)
            : new WrappedApiResponse(response, false, responseMessage);
    }

    public synchronized WrappedApiResponse tryLogin(String username, String password)
    {
        String apiKey;
        WrappedApiResponse responseObject;

        try {
            var connection = HttpConnectionFactory.CreateSecure(
                    new GenericGetRequest().setMethod("GET").setHostUrl("delegate"),
                    new ApiAuthenticationContext(username, password));

            responseObject = this.readWrappedApiResponseOrDie(connection);

            if (!responseObject.getSuccess()) {
                return responseObject;
            }
        } catch (Exception ex) {
            System.out.println("Failed to establish authentication request.");
            ex.printStackTrace();

            return new WrappedApiResponse(false, "An error occurred while trying to authenticate.");
        }

        apiKey = responseObject.getData();

        instance.setAuthenticationContext(new ApiAuthenticationContext(username, password, apiKey));

        System.out.println("Logged in as: " + username);
        return new WrappedApiResponse(true, "Success!");
    }

    public synchronized <X extends BaseApiRequest> WrappedApiResponse DeleteAsync(X request)
            throws MalformedURLException, IOException
    {
        var connection = HttpConnectionFactory.CreateSecure(
                request.setMethod("DELETE").setHostUrl("author/9a70991f-250d-4ee2-b883-d833b0a47b23"),
                this.authenticationContext);

        connection.connect();

        var responseCode = connection.getResponseCode();
        var responseMessage = connection.getResponseMessage();

        System.out.println("Response: " + responseCode + " - " + responseMessage);

        return new WrappedApiResponse();
    }
}
