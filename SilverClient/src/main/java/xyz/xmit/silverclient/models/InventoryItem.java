package xyz.xmit.silverclient.models;

import java.util.UUID;

public final class InventoryItem
    extends BaseModelSoftDeletes<UUID>
{
    @DataField(lengthLimit = 255)
    public String title;

    @DataField
    public String description;

    @Override
    public String getBaseModelUri() {
        return "item";
    }
}
