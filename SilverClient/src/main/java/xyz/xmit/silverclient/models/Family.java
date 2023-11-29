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
        if (this.isNewModel) {
            ApiFacade.handleApiPost(this, Family.class);
        } else {
            ApiFacade.handleApiPut(this, Family.class);
        }
    }
}
