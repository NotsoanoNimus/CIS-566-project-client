package xyz.xmit.silverclient.observer;

import xyz.xmit.silverclient.models.BaseModel;

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
    public synchronized void subscribe(BaseSubscriber subscriber)
    {
        this.subscribers.add(subscriber);
    }

    /**
     * Unregister a current Subscriber from the Publisher.
     * @param subscriber The Subscriber to remove.
     */
    public synchronized void unsubscribe(BaseSubscriber subscriber)
    {
        this.subscribers.remove(subscriber);
    }

    /**
     * Notify all subscribers immediately.
     */
    public synchronized void commitAll()
    {
        this.subscribers.forEach(BaseSubscriber::commit);

        this.subscribers.clear();
    }

    /**
     * Unsubscribe all registered Subscribers immediately and indiscriminately.
     */
    public synchronized void unsubscribeAll()
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

    /**
     * Returns whether the list of Subscribers already has a member with that unique ID and model type.
     * <br />
     * TODO: This starts to couple subscribers in the Observer to Model types specifically. But we don't
     * want that. Find a different way to do this. Probably with a better concrete publisher.
     */
    @SuppressWarnings("unchecked")
    public synchronized <TModel extends BaseModel<?>> boolean isUniqueModelAlreadySubscribed(TModel entity, Class<TModel> clazz)
    {
        return entity.getPrimaryId() != null
            && this.subscribers.stream()
                .filter(s -> s.getBaseModelClass() != null && s.getBaseModelClass().equals(clazz))
                .anyMatch(s -> ((TModel)s).getPrimaryId().equals(entity.getPrimaryId()));
    }

    /**
     * If found, unsubscribes a unique model entity from the list of Subscribers.
     */
    @SuppressWarnings("unchecked")
    public synchronized <TModel extends BaseModel<?>> void unsubscribeUniqueModelById(TModel entity, Class<TModel> clazz)
    {
        if (!this.isUniqueModelAlreadySubscribed(entity, clazz) || entity.getPrimaryId() == null) return;

        var toUnsubscribe = this.subscribers.stream()
                .filter(s -> s.getBaseModelClass() != null && s.getBaseModelClass().equals(clazz))
                .filter(s -> ((TModel)s).getPrimaryId().equals(entity.getPrimaryId()))
                .toList();

        toUnsubscribe.forEach(SilverPublisher.getInstance()::unsubscribe);
    }
}
