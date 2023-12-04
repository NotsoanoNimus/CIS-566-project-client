package xyz.xmit.silverclient.api.request;

import xyz.xmit.silverclient.models.BaseModel;

public final class GenericPutRequest<TModel extends BaseModel<?>>
        extends BaseApiRequest
{
    public GenericPutRequest(TModel model, Class<TModel> modelClazz) throws Exception
    {
        this.setMethod("PUT");

        this.setHostUrl(modelClazz.getConstructor().newInstance().getBaseModelUri() + "/" + String.valueOf(model.id));
    }

    public GenericPutRequest()
    {
        this.setMethod("PUT");
    }
}
