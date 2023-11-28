package xyz.xmit.silverclient.models;

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
}
