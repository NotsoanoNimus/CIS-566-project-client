package xyz.xmit.silverclient.api;

import xyz.xmit.silverclient.api.request.BaseApiRequest;
import xyz.xmit.silverclient.api.request.GenericGetRequest;
import xyz.xmit.silverclient.api.response.BaseApiResponse;
import xyz.xmit.silverclient.api.response.GenericSuccessResponse;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
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
            sc.init(null, new TrustManager[]{trm}, null);

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

    public synchronized boolean tryLogin(String username, String password)
    {
        String apiKey = "";

        try {
            var connection = HttpConnectionFactory.CreateSecure(
                    new GenericGetRequest().setMethod("GET").setHostUrl(""),
                    new ApiAuthenticationContext(username, password));

            connection.connect();

            var responseCode = connection.getResponseCode();
            var responseMessage = connection.getResponseMessage();
        } catch (Exception ex) {
            System.out.println("Failed to establish authentication request.");

            return false;
        }

        instance.setAuthenticationContext(new ApiAuthenticationContext(username, password, apiKey));

        System.out.println("Logged in as: " + username);
        return true;
    }

    public synchronized <X extends BaseApiRequest> BaseApiResponse DeleteAsync(X request)
            throws MalformedURLException, IOException
    {
        var connection = HttpConnectionFactory.CreateSecure(
                request.setMethod("DELETE").setHostUrl("author/9a70991f-250d-4ee2-b883-d833b0a47b23"),
                this.authenticationContext);

        connection.connect();

        var responseCode = connection.getResponseCode();
        var responseMessage = connection.getResponseMessage();

        System.out.println("Response: " + responseCode + " - " + responseMessage);

        return new GenericSuccessResponse(responseMessage);
    }
}
