package xyz.xmit.silverclient.models;

import java.util.UUID;

public final class Author
    extends BaseModelSoftDeletes<UUID>
{
    @DataField
    public String name;

    @Override
    public String getBaseModelUri() {
        return "author";
    }
}
