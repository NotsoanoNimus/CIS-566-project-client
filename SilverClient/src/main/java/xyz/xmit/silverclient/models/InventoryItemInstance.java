package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.xmit.silverclient.api.ApiFacade;

import java.util.Date;
import java.util.UUID;

public final class InventoryItemInstance
    extends BaseModelSoftDeletes<UUID>
{
    public InventoryItemEdition edition;

    public String state;

    @DataField
    public UUID inventory_item_edition_id;

    @DataField
    public int barcode_sku;

    @DataField
    public String item_description;

    @DataField
    public Date acquired_at;

    @DataField(lengthLimit = 255)
    public String condition;

    @DataField(lengthLimit = 255)
    public String status;

    @DataField
    public double buyout_price;

    @Override
    @JsonIgnore
    public String getBaseModelUri() {
        return "item-edition-instance";
    }

    public String getTitle()
    {
        return this.edition.item.title;
    }

    public String getEdition()
    {
        return this.edition.edition;
    }

    public String getSku()
    {
        return String.valueOf(this.barcode_sku);
    }

    public String getState()
    {
        return this.state;
    }

    public String getCondition()
    {
        return this.condition;
    }

    public Date getAcquiredAt()
    {
        return this.acquired_at;
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, InventoryItemInstance.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return InventoryItemInstance.class;
    }
}
