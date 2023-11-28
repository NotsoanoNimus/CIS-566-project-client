package xyz.xmit.silverclient.models;

import java.util.List;
import java.util.UUID;

public final class Author
    extends BaseModelSoftDeletes<UUID>
{
    public List<Tag> tags;

    @DataField
    public String name;

    @Override
    public String getBaseModelUri() {
        return "author";
    }
}
