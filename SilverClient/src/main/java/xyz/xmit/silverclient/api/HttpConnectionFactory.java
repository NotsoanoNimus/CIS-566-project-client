package xyz.xmit.silverclient.api;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public final class HttpConnectionFactory
{
    public static <T extends HttpRequestable> HttpURLConnection Create(
            T fromRequest,
            ApiAuthenticationContext authenticationContext)
            throws MalformedURLException, IOException
    {
        return HttpConnectionFactory.ApplyDefaultConnectionOptions(fromRequest, authenticationContext);
    }

    public static <T extends HttpRequestable> HttpsURLConnection CreateSecure(
            T fromRequest,
            ApiAuthenticationContext authenticationContext)
            throws MalformedURLException, IOException
    {
        return (HttpsURLConnection)HttpConnectionFactory.ApplyDefaultConnectionOptions(fromRequest, authenticationContext);
    }

    private static <T extends HttpRequestable> HttpURLConnection ApplyDefaultConnectionOptions(
            T fromRequest,
            ApiAuthenticationContext authenticationContext)
            throws MalformedURLException, IOException
    {
        var serverAddress = new URL("https://localhost/client-api/"+fromRequest.getHostUrl());

        var urlConnection = (HttpsURLConnection)serverAddress.openConnection();

        urlConnection.setRequestMethod(fromRequest.getMethod());
        urlConnection.setReadTimeout(20_000);

        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);

        urlConnection.setUseCaches(false);

        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Accept", "application/json");

        urlConnection.setRequestProperty("X-Api-Key", authenticationContext.getApiKey());

        var encodedCreds = Base64.getEncoder().encodeToString(
                (authenticationContext.getUsername() + ":" + authenticationContext.getPassword()).getBytes());
        urlConnection.setRequestProperty("Authorization", "Basic " + encodedCreds);

        return urlConnection;
    }
}
