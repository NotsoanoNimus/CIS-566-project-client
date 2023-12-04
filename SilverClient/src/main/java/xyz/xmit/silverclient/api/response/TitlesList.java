package xyz.xmit.silverclient.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.xmit.silverclient.models.InventoryItem;

import java.util.List;

public final class TitlesList
{
    @JsonProperty
    public List<InventoryItem> titles;
}
