package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.xmit.silverclient.api.ApiFacade;

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
    public Date checked_out_at;

    @DataField
    public Date due_at;

    @DataField
    public Date returned_at;

    @DataField
    public boolean is_locked;

    @DataField
    public String notes;

    @Override
    @JsonIgnore
    public String getBaseModelUri() {
        return "checkout";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Checkout.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return Checkout.class;
    }
}
