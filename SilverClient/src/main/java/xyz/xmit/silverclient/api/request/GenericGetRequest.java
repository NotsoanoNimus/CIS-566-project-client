package xyz.xmit.silverclient.api.request;

import xyz.xmit.silverclient.models.BaseModel;

public final class GenericGetRequest<TId, TModel extends BaseModel<TId>>
        extends BaseApiRequest
{
    public GenericGetRequest(TId objectId, Class<TModel> modelClazz) throws Exception
    {
        this.setMethod("GET");

        this.setHostUrl(modelClazz.getConstructor().newInstance().getBaseModelUri() + "/" + objectId.toString());
    }

    public GenericGetRequest()
    {
        this.setMethod("GET");
    }
}
