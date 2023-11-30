package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    public String getBaseModelUri() {
        return "user";
    }

    @Override
    public void commit()
    {
        if (this.isNewModel) {
            ApiFacade.handleApiPost(this, User.class);
        } else {
            ApiFacade.handleApiPut(this, User.class);
        }
    }
}
