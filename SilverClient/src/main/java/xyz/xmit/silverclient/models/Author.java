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
    public String getBaseModelUri() {
        return "author";
    }

    @Override
    public void commit()
    {
        if (this.isNewModel) {
            ApiFacade.handleApiPost(this, Author.class);
        } else {
            ApiFacade.handleApiPut(this, Author.class);
        }
    }
}
