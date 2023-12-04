package xyz.xmit.silverclient.observer;

import xyz.xmit.silverclient.models.BaseModel;

import java.util.UUID;

public abstract class BaseSubscriber
{
    private UUID uniqueSubscriberId = UUID.randomUUID();

    public UUID getUniqueSubscriberId()
    {
        return this.uniqueSubscriberId;
    }

    public abstract void commit();

    public Class<? extends BaseModel<?>> getBaseModelClass()
    {
        return null;
    }
}
