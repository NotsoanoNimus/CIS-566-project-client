package xyz.xmit.silverclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class Tag
    extends BaseModelTimestamps<Integer>
{
    @JsonIgnore
    public Object pivot;

    @DataField(lengthLimit = 255)
    public String name;

    @DataField(lengthLimit = 255)
    public String slug;

    @Override
    public String getBaseModelUri() {
        return "tag";
    }
}
