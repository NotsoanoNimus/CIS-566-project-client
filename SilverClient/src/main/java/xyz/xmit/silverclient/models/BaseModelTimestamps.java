package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public abstract class BaseModelTimestamps<TPrimary>
    extends BaseModel<TPrimary>
    implements Model
{
    @JsonProperty
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @DataField
    public Date created_at = null;

    @JsonProperty
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @DataField
    public Date updated_at = null;
}
