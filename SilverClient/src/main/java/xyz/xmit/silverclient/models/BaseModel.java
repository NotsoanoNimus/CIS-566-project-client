package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.xmit.silverclient.api.HttpApiClient;
import xyz.xmit.silverclient.observer.BaseSubscriber;

public abstract class BaseModel<TPrimary>
    extends BaseSubscriber
    implements Model
{
    @JsonProperty
    @DataField
    public TPrimary id;

    @JsonIgnore
    protected boolean isNewModel = false;

    @JsonIgnore
    public abstract String getBaseModelUri();

    @JsonIgnore
    public TPrimary getPrimaryId()
    {
        return this.id;
    }

    @JsonIgnore
    public void setNewModel(boolean isNewModel)
    {
        this.isNewModel = isNewModel;
    }
}
