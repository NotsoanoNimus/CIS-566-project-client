package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.xmit.silverclient.api.ApiFacade;

import java.util.Date;
import java.util.UUID;

public final class InventoryItemEdition
    extends BaseModelSoftDeletes<UUID>
{
    public InventoryItem item;

    @DataField
    public UUID inventory_item_id;

    @DataField(lengthLimit = 255)
    public String edition;

    @DataField
    public String edition_details;

    @DataField(lengthLimit = 255)
    public String language;

    @DataField(lengthLimit = 255)
    public String content_length;

    @DataField(lengthLimit = 255)
    public String isn;

    @DataField(lengthLimit = 255)
    public String format;

    @DataField(lengthLimit = 255)
    public String publisher;

    @DataField
    public Date published_at;

    @Override
    @JsonIgnore
    public String getBaseModelUri() {
        return "item-edition";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, InventoryItemEdition.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return InventoryItemEdition.class;
    }
}
