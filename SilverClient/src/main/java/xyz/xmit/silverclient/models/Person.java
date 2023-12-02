package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public Integer user_id;

    @DataField
    public UUID family_id;

    @DataField(lengthLimit = 255)
    public String first_name;

    @DataField(lengthLimit = 255)
    public String middle_names;

    @DataField(lengthLimit = 255)
    public String last_name;

    @JsonIgnore
    public String display_name;

    @JsonIgnore
    public String display_name_full;

    @JsonIgnore
    public String initials;

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
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Person.class, false);
    }

    public boolean isUserBanned()
    {
        return this.deleted_at != null || this.user.deleted_at != null;
    }

    public String getDisplayName()
    {
        return this.first_name + " " + (this.middle_names != null ? (this.middle_names + " ") : "") + this.last_name;
    }

    public String getEmail()
    {
        return this.user.email;
    }

    public String getIdentifier()
    {
        return String.valueOf(this.barcode_identifier);
    }

    public String getLocation()
    {
        return this.city + ", " + this.state + " - " + this.country;
    }

    public String getStatus()
    {
        return this.isUserBanned() ? "Banned"
                : (
                        this.user.is_staff
                                ? "Staff"
                                : (
                                        this.user.last_activity_at != null
                                                ? "Active"
                                                : "Dormant"
                        )
        );
    }
}
