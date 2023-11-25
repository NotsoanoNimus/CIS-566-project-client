package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public abstract class BaseModelTimestamps<TPrimary>
    implements Model
{
    @JsonProperty
    @DataField
    public TPrimary id;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @DataField
    public Date created_at = null;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @DataField
    public Date updated_at = null;

    @JsonIgnore
    public abstract String getBaseModelUri();

    @JsonIgnore
    public TPrimary getPrimaryId()
    {
        return this.id;
    }
}
