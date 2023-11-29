package xyz.xmit.silverclient.models;

import xyz.xmit.silverclient.api.ApiFacade;

import java.util.List;
import java.util.UUID;

public final class Person
    extends BaseModelSoftDeletes<UUID>
{
    public List<PersonAnnotation> notes;

    public User user;

    public Family family;

    @DataField
    public int barcode_identifier;

    @DataField
    public UUID user_id;

    @DataField
    public UUID family_id;

    @DataField(lengthLimit = 255)
    public String first_name;

    @DataField(lengthLimit = 255)
    public String middle_names;

    @DataField(lengthLimit = 255)
    public String last_name;

    @DataField(lengthLimit = 255)
    public String address_line_1;

    @DataField(lengthLimit = 255)
    public String address_line_2;

    @DataField(lengthLimit = 255)
    public String city;

    @DataField(lengthLimit = 255)
    public String state;

    @DataField(lengthLimit = 255)
    public String country;

    @DataField(lengthLimit = 255)
    public String zip_code;

    @DataField
    public boolean is_head_of_family;

    @Override
    public String getBaseModelUri() {
        return "person";
    }

    @Override
    public void commit()
    {
        if (this.isNewModel) {
            ApiFacade.handleApiPost(this, Person.class);
        } else {
            ApiFacade.handleApiPut(this, Person.class);
        }
    }
}
