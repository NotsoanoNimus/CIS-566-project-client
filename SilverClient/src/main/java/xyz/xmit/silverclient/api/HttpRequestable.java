package xyz.xmit.silverclient.api;

import xyz.xmit.silverclient.models.BaseModel;

public interface HttpRequestable
{
    public String getMethod();

    public String getHostUrl();

    public BaseModel<?> getModel();
}
