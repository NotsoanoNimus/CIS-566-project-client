package xyz.xmit.silverclient.api;

import xyz.xmit.silverclient.api.request.BaseApiRequest;
import xyz.xmit.silverclient.api.response.BaseApiResponse;


public final class HttpApiClient
{
    private static HttpApiClient instance;

    private HttpApiClient() {}

    public static HttpApiClient getInstance()
    {
        return HttpApiClient.instance == null
                ? (HttpApiClient.instance = new HttpApiClient())
                : HttpApiClient.instance;
    }

    public synchronized <X extends BaseApiRequest, Y extends BaseApiResponse> Y DeleteAsync(
            X request,
            ApiAuthenticationContext context)
    {
        HttpConnectionFactory.CreateSecure().connect();
    }
}
