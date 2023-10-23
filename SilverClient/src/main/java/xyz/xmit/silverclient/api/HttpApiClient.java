package xyz.xmit.silverclient.api;

import xyz.xmit.silverclient.api.request.BaseApiRequest;
import xyz.xmit.silverclient.api.response.BaseApiResponse;
import xyz.xmit.silverclient.api.response.GenericSuccessResponse;

import java.io.IOException;
import java.net.MalformedURLException;


public final class HttpApiClient
{
    private static HttpApiClient instance;

    private ApiAuthenticationContext authenticationContext;

    private HttpApiClient() {}

    public static HttpApiClient getInstance()
    {
        return HttpApiClient.instance == null
                ? (HttpApiClient.instance = new HttpApiClient())
                : HttpApiClient.instance;
    }

    public synchronized void setAuthenticationContext(ApiAuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public synchronized <X extends BaseApiRequest> BaseApiResponse DeleteAsync(X request)
            throws MalformedURLException, IOException
    {
        var connection = HttpConnectionFactory.CreateSecure(
                request.setMethod("DELETE").setHostUrl("author/9a70991f-250d-4ee2-b883-d833b0a47b23"),
                this.authenticationContext);

        connection.connect();

        var response = connection.getResponseCode();
        var responseMessage = connection.getResponseMessage();

        System.out.println("Response: " + response + " - " + responseMessage);

        return new GenericSuccessResponse(responseMessage);
    }
}
