package xyz.xmit.silverclient.observer;

import java.util.List;

public abstract class BasePublisher
{
    protected List<BaseSubscriber> subscribers;

    public void subscribe(BaseSubscriber subscriber)
    {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(BaseSubscriber subscriber)
    {
        this.subscribers.remove(subscriber);
    }

    public void commitAll()
    {
        this.subscribers.forEach(BaseSubscriber::commit);
    }

    public List<BaseSubscriber> getSubscribers()
    {
        return this.subscribers;
    }
}
