package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.xmit.silverclient.api.ApiFacade;

import java.util.UUID;

public final class Charge
    extends BaseModelSoftDeletes<UUID>
{
    public Checkout checkout;

    @JsonIgnore
    public boolean is_paid;

    @JsonIgnore
    public double amount_paid;

    @DataField
    public UUID checkout_id;

    @DataField
    public double amount;

    @DataField
    public String notes;

    @Override
    @JsonIgnore
    public String getBaseModelUri() {
        return "charge";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Charge.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return Charge.class;
    }
}
