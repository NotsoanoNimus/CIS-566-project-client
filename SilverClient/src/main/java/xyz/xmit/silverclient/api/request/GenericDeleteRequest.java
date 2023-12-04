package xyz.xmit.silverclient.api.request;

import xyz.xmit.silverclient.models.BaseModel;

public final class GenericDeleteRequest<TModel extends BaseModel<?>>
        extends BaseApiRequest
{
    public GenericDeleteRequest(TModel model, Class<TModel> modelClazz) throws Exception
    {
        this.setMethod("DELETE");

        this.setHostUrl(modelClazz.getConstructor().newInstance().getBaseModelUri() + "/" + String.valueOf(model.id));
    }

    public GenericDeleteRequest()
    {
        this.setMethod("DELETE");
    }
}
