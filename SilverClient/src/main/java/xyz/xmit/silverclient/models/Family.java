package xyz.xmit.silverclient.models;

import xyz.xmit.silverclient.api.ApiFacade;

import java.util.List;
import java.util.UUID;

public final class Family
    extends BaseModelSoftDeletes<UUID>
{
    public List<Person> members;

    @DataField
    public String surname;

    @Override
    public String getBaseModelUri() {
        return "family";
    }

    @Override
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Family.class, false);
    }
}
