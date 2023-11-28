package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseModel<TPrimary>
    implements Model
{
    @JsonProperty
    @DataField
    public TPrimary id;

    @JsonIgnore
    public abstract String getBaseModelUri();

    @JsonIgnore
    public TPrimary getPrimaryId()
    {
        return this.id;
    }
}
