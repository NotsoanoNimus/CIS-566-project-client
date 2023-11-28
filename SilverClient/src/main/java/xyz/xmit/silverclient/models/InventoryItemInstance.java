package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;

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
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date acquired_at;

    @DataField(lengthLimit = 255)
    public String condition;

    @DataField(lengthLimit = 255)
    public String status;

    @DataField
    public double buyout_price;

    @Override
    public String getBaseModelUri() {
        return "item-edition-instance";
    }
}
