package xyz.xmit.silverclient.observer;

import java.util.List;

public final class SilverPublisher
    extends BasePublisher
{
    private static SilverPublisher instance = null;

    public static SilverPublisher getInstance()
    {
        if (instance == null) {
            instance = new SilverPublisher();
        }

        return instance;
    }

    // Overridden just to show the extensibility of the base class.
    @Override
    public List<BaseSubscriber> getSubscribers()
    {
        System.out.println("Subscribers registered: " + this.subscribers.size());

        return this.subscribers;
    }
}
