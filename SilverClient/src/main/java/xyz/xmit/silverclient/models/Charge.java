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
    public String getBaseModelUri() {
        return "charge";
    }

    @Override
    public void commit()
    {
        if (this.isNewModel) {
            ApiFacade.handleApiPost(this, Charge.class);
        } else {
            ApiFacade.handleApiPut(this, Charge.class);
        }
    }
}
