package xyz.xmit.silverclient.api;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class HttpConnectionFactory
{
    public static <T extends HttpRequestable> HttpURLConnection Create(T fromRequest)
            throws MalformedURLException, IOException
    {
        return (HttpsURLConnection)HttpConnectionFactory.ApplyDefaultConnectionOptions(fromRequest);
    }

    public static <T extends HttpRequestable> HttpsURLConnection CreateSecure(T fromRequest)
            throws MalformedURLException, IOException
    {
        return (HttpsURLConnection)HttpConnectionFactory.ApplyDefaultConnectionOptions(fromRequest);
    }

    private static <T extends HttpRequestable> HttpURLConnection ApplyDefaultConnectionOptions(T fromRequest)
            throws MalformedURLException, IOException
    {
        var serverAddress = new URL(fromRequest.getHostUrl());

        var urlConnection = (HttpURLConnection)serverAddress.openConnection();

        urlConnection.setRequestMethod(fromRequest.getMethod());
        urlConnection.setReadTimeout(20_000);

        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);

        urlConnection.setUseCaches(false);

        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("X-Api-Key", "MilkAndEggs");

        return urlConnection;
    }
}
