package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
}
