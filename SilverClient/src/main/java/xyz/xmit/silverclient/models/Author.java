package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.xmit.silverclient.api.ApiFacade;
import xyz.xmit.silverclient.api.HttpApiClient;

import java.util.List;
import java.util.UUID;

public final class Author
    extends BaseModelSoftDeletes<UUID>
{
    @JsonIgnore
    public Object pivot;

    public List<Tag> tags;

    @DataField
    public String name;

    @Override
    @JsonIgnore
    public String getBaseModelUri() {
        return "author";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Author.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return Author.class;
    }
}
