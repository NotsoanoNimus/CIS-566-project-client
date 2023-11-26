package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public final class Checkout
    extends BaseModel<UUID>
{
    public Person person;

    public InventoryItemInstance instance;

    @DataField
    public UUID person_id;

    @DataField
    public UUID inventory_item_instance_id;

    @DataField
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date checked_out_at;

    @DataField
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date due_at;

    @DataField
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date returned_at;

    @DataField
    public boolean is_locked;

    @DataField
    public String notes;

    @Override
    public String getBaseModelUri() {
        return "checkout";
    }
}
