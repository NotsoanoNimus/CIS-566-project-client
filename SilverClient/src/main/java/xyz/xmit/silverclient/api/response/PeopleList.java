package xyz.xmit.silverclient.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.xmit.silverclient.models.Person;

import java.util.List;

public final class PeopleList
{
    @JsonProperty
    public List<Person> people;
}
