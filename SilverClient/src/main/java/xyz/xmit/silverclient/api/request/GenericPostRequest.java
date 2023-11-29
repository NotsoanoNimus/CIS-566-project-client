package xyz.xmit.silverclient.api.request;

import xyz.xmit.silverclient.models.BaseModel;

public final class GenericPostRequest<TModel extends BaseModel<?>>
        extends BaseApiRequest
{
    public GenericPostRequest(TModel model, Class<TModel> modelClazz) throws Exception
    {
        this.setMethod("POST");

        this.setHostUrl(modelClazz.getConstructor().newInstance().getBaseModelUri() + "/");
    }

    public GenericPostRequest()
    {
        this.setMethod("POST");
    }
}