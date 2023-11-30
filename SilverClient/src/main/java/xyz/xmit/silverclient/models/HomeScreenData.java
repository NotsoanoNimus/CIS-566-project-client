package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class HomeScreenData
{
    @JsonProperty
    public List<InventoryItem> titles;

    @JsonProperty
    public List<Person> people;

    @JsonProperty
    public List<InventoryItemInstance> instances;
}
