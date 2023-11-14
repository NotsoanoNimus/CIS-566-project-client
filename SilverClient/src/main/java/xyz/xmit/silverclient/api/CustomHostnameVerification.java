package xyz.xmit.silverclient.api;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public final class CustomHostnameVerification implements HostnameVerifier
{
    public final static CustomHostnameVerification instance = new CustomHostnameVerification();

    private final HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

    public CustomHostnameVerification() {}

    @Override
    public boolean verify(String hostname, SSLSession session) {
        if (hostname.startsWith("192.168.")
                || hostname.startsWith("172.16.")
                || hostname.startsWith("10.")
                || hostname.startsWith("silver.xmit.xyz")
                || hostname.equalsIgnoreCase("localhost")) {
            return true;
        }

        return this.defaultHostnameVerifier.verify(hostname, session);
    }
}
