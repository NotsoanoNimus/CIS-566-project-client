package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.xmit.silverclient.api.ApiFacade;
import xyz.xmit.silverclient.api.HttpApiClient;

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
    @JsonIgnore
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
    @JsonIgnore
    public String getBaseModelUri() {
        return "person";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, Person.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return Person.class;
    }

    @JsonIgnore
    public boolean isUserBanned()
    {
        return this.deleted_at != null || this.user.deleted_at != null;
    }

    @JsonIgnore
    public boolean isUserStaff()
    {
        return this.user.is_staff;
    }

    @JsonIgnore
    public boolean isUserSelf()
    {
        return this.user.email.equalsIgnoreCase(HttpApiClient.getInstance().getAuthenticationContext().getUsername());
    }

    @JsonIgnore
    public String getDisplayName()
    {
        return this.first_name + " " + (this.middle_names != null ? (this.middle_names + " ") : "") + this.last_name;
    }

    @JsonIgnore
    public String getEmail()
    {
        return this.user.email;
    }

    @JsonIgnore
    public String getIdentifier()
    {
        return String.valueOf(this.barcode_identifier);
    }

    @JsonIgnore
    public String getLocation()
    {
        return this.city + ", " + this.state + " - " + this.country;
    }

    @JsonIgnore
    public String getStatus()
    {
        return this.isUserBanned()
                ? "Banned"
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

    // TODO: Abstract this into a 'clone()' by JSON interface.
    @Override
    @JsonIgnore
    public Person clone()
    {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(objectMapper.writeValueAsString(this), Person.class);
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
}
