package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public final class Author
    extends BaseModelWithPrimary<UUID>
{
    @DataField
    public String name;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @DataField
    public Date deleted_at = null;

    @Override
    public String getBaseModelUri() {
        return "author";
    }
}
