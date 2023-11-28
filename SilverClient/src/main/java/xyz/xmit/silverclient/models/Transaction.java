package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;

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
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date paid_at;

    @Override
    public String getBaseModelUri() {
        return "transaction";
    }
}
