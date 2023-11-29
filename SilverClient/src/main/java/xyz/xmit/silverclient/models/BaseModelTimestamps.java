package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public abstract class BaseModelTimestamps<TPrimary>
    extends BaseModel<TPrimary>
    implements Model
{
    @JsonProperty
    @DataField
    public Date created_at = null;

    @JsonProperty
    @DataField
    public Date updated_at = null;
}
