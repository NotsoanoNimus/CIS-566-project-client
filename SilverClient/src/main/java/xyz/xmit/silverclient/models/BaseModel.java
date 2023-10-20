package xyz.xmit.silverclient.models;

public abstract class BaseModel<TPrimary>
{
    protected TPrimary id;

    protected boolean softDeletes = true;

    public BaseModel() {}

    public BaseModel(boolean softDeletes)
    {
        this.softDeletes = softDeletes;
    }

    public abstract String getBaseModelUri();

    public TPrimary getPrimaryId()
    {
        return this.id;
    }
}
