package xyz.xmit.silverclient.models;

import xyz.xmit.silverclient.api.ApiFacade;

import java.util.List;
import java.util.UUID;

public final class InventoryItem
    extends BaseModelSoftDeletes<UUID>
{
    public List<Author> authors;

    public List<Tag> tags;

    @DataField(lengthLimit = 255)
    public String title;

    @DataField
    public String description;

    @Override
    public String getBaseModelUri() {
        return "item";
    }

    @Override
    public void commit()
    {
        if (this.isNewModel) {
            ApiFacade.handleApiPost(this, InventoryItem.class);
        } else {
            ApiFacade.handleApiPut(this, InventoryItem.class);
        }
    }
}
