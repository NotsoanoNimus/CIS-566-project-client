package xyz.xmit.silverclient.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.xmit.silverclient.models.InventoryItemInstance;

import java.util.List;

public final class InstancesList
{
    @JsonProperty
    public List<InventoryItemInstance> instances;
}
