package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.xmit.silverclient.api.ApiFacade;

import java.util.Date;
import java.util.UUID;

public final class Transaction
    extends BaseModel<UUID>
{
    public Charge charge;

    @DataField
    public UUID charge_id;

    @DataField
    public double amount;

    @DataField
    public Date paid_at;

    @Override
    @JsonIgnore
    public String getBaseModelUri() {
        return "transaction";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Transaction.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return Transaction.class;
    }
}
