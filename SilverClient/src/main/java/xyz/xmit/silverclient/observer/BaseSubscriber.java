package xyz.xmit.silverclient.observer;

import java.util.UUID;

public abstract class BaseSubscriber
{
    private UUID uniqueSubscriberId = UUID.randomUUID();

    public UUID getUniqueSubscriberId()
    {
        return this.uniqueSubscriberId;
    }

    public abstract void commit();
}
