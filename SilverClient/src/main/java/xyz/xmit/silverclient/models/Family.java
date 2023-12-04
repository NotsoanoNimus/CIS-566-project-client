package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    public String getBaseModelUri() {
        return "family";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Family.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return Family.class;
    }
}
