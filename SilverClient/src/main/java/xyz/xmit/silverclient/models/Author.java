package xyz.xmit.silverclient.models;

import java.util.UUID;

public final class Author
    extends BaseModel<UUID>
{
    @Override
    public String getBaseModelUri() {
        return "author";
    }
}
