package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date last_login_at;

    @DataField
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date last_activity_at;

    @Override
    public String getBaseModelUri() {
        return "user";
    }
}
