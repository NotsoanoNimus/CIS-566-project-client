package xyz.xmit.silverclient.api.request;

import xyz.xmit.silverclient.api.HttpRequestable;

import java.io.Serializable;

public abstract class BaseApiRequest
        implements Serializable, HttpRequestable
{
    private String method;

    private String targetHost;

    @Override
    public String getMethod() {
        return this.method;
    }

    public BaseApiRequest setMethod(String method) {
        this.method = method;

        return this;
    }

    @Override
    public String getHostUrl() {
        return this.targetHost;
    }

    public BaseApiRequest setHostUrl(String url) {
        this.targetHost = url;

        return this;
    }
}
