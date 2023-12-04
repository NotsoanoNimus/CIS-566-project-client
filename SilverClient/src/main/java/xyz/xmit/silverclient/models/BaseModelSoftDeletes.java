package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public abstract class BaseModelSoftDeletes<TPrimary>
    extends BaseModel<TPrimary>
    implements Model
{
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DataField
    public Date created_at = null;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DataField
    public Date updated_at = null;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @DataField(hidden = true)
    public Date deleted_at = null;
}
