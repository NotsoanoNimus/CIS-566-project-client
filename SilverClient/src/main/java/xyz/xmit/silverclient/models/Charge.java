package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public final class Charge
    extends BaseModelSoftDeletes<UUID>
{
    public Checkout checkout;

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
}
