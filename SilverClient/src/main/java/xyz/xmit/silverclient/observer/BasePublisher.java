package xyz.xmit.silverclient.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePublisher
{
    protected List<BaseSubscriber> subscribers = new ArrayList<>();

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

    public void unsubscribeAll()
    {
        this.subscribers.clear();
    }

    public boolean hasSubscribers()
    {
        return !this.subscribers.isEmpty();
    }

    public List<BaseSubscriber> getSubscribers()
    {
        return this.subscribers;
    }
}
