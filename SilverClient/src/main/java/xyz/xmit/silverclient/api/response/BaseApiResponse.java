package xyz.xmit.silverclient.api.response;

public abstract class BaseApiResponse
{
    public boolean Success = false;

    public String Data = null;

    public BaseApiResponse(String jsonResponse)
    {
    }
}
