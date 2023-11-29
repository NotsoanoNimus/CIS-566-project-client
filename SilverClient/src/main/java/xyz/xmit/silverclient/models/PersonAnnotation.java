package xyz.xmit.silverclient.models;

import xyz.xmit.silverclient.api.ApiFacade;

import java.util.UUID;

public final class PersonAnnotation
    extends BaseModelSoftDeletes<UUID>
{
    public Person about;

    public Person by;

    @DataField
    public UUID person_id;

    @DataField
    public UUID about_person_id;

    @DataField
    public String note;

    @Override
    public String getBaseModelUri() {
        return "person-annotation";
    }

    @Override
    public void commit()
    {
        if (this.isNewModel) {
            ApiFacade.handleApiPost(this, PersonAnnotation.class);
        } else {
            ApiFacade.handleApiPut(this, PersonAnnotation.class);
        }
    }
}
