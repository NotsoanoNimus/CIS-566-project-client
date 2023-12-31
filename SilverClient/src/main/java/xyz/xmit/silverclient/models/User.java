package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.xmit.silverclient.api.ApiFacade;

import java.util.Date;

public final class User
    extends BaseModelSoftDeletes<Integer>
{
    public Person person;

    @DataField
    public boolean is_staff;

    @DataField(lengthLimit = 255)
    public String email;

    @DataField
    public Date last_login_at;

    @DataField
    public Date last_activity_at;

    public Date email_verified_at;

    @Override
    @JsonIgnore
    public String getBaseModelUri() {
        return "user";
    }

    @Override
    @JsonIgnore
    public void commit()
    {
        ApiFacade.safeApiRequest(this.isNewModel ? "POST" : "PUT", this, User.class, false);
    }

    @Override
    @JsonIgnore
    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return User.class;
    }
}
