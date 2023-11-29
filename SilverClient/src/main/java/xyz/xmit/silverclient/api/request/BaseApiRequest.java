package xyz.xmit.silverclient.api.request;

import xyz.xmit.silverclient.api.HttpRequestable;
import xyz.xmit.silverclient.models.BaseModel;

import java.io.Serializable;

public abstract class BaseApiRequest
        implements Serializable, HttpRequestable
{
    private String method;

    private String targetHost;

    private BaseModel<?> model;

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

    @Override
    public BaseModel<?> getModel()
    {
        return this.model;
    }

    public BaseApiRequest setModel(BaseModel<?> model)
    {
        this.model = model;

        return this;
    }
}
