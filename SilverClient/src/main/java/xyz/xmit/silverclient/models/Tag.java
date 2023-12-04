package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.xmit.silverclient.api.ApiFacade;

public final class Tag
    extends BaseModelTimestamps<Integer>
{
    @JsonIgnore
    public Object pivot;

    @DataField(lengthLimit = 255)
    public String name;

    @DataField(lengthLimit = 255)
    public String slug;

    @Override
    @JsonIgnore
    public String getBaseModelUri() {
        return "tag";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Tag.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return Tag.class;
    }
}
