package xyz.xmit.silverclient.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract version of an Observer publisher. See the SilverPublisher concrete class
 * declaration for information about the implementation of the pattern. You can still
 * find information about Publisher methods on this class.
 */
public abstract class BasePublisher
{
    /**
     * The list of current subscribers at any given time.
     */
    protected List<BaseSubscriber> subscribers = new ArrayList<>();

    /**
     * Register a new Subscriber to the Publisher.
     * @param subscriber The Subscriber to notify.
     */
    public void subscribe(BaseSubscriber subscriber)
    {
        this.subscribers.add(subscriber);
    }

    /**
     * Unregister a current Subscriber from the Publisher.
     * @param subscriber The Subscriber to remove.
     */
    public void unsubscribe(BaseSubscriber subscriber)
    {
        this.subscribers.remove(subscriber);
    }

    /**
     * Notify all subscribers immediately.
     */
    public void commitAll()
    {
        this.subscribers.forEach(BaseSubscriber::commit);
    }

    /**
     * Unsubscribe all registered Subscribers immediately and indiscriminately.
     */
    public void unsubscribeAll()
    {
        this.subscribers.clear();
    }

    /**
     * Returns whether this Publisher currently has any registered Subscribers.
     */
    public boolean hasSubscribers()
    {
        return !this.subscribers.isEmpty();
    }

    /**
     * Returns the list of currently registered Subscribers.
     */
    public List<BaseSubscriber> getSubscribers()
    {
        return this.subscribers;
    }
}
