package xyz.xmit.silverclient.models;

public final class Tag
    extends BaseModelTimestamps<Integer>
{
    @DataField(lengthLimit = 255)
    public String name;

    @DataField(lengthLimit = 255)
    public String slug;

    @Override
    public String getBaseModelUri() {
        return "tag";
    }
}
