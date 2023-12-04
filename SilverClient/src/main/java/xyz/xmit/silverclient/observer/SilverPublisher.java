package xyz.xmit.silverclient.observer;

import java.util.List;

/**
 * DESIGN PATTERN: Observer (Behavioral)
 * <br /><br />
 * The Observer pattern is used to track different objects in any of the application's
 * various states which have been modified. This is in theory and an ideal approach. In
 * practice, however, the Observer is 'watching' the set of dashboard data items which
 * register themselves to the global Publisher instance as Subscribers.
 * <br /><br />
 * It is mainly used to detect when there are pending changes which have not yet been sent
 * to the remote server API. It is helpful for notifying users on application exit that
 * they are about to close the client without submitting their 'saved for later' changes.
 * <br /><br />
 * <strong>NOTE</strong>: This class also incorporates the Singleton pattern to maintain a
 * global list of registrations. See the 'instance' private property of the class for more
 * information about why.
 */
public final class SilverPublisher
    extends BasePublisher
{
    /**
     * Mix in the Singleton pattern here to create a single, universal concrete Publisher.
     * <br /><br />
     * In practice, this would never be done, but because the application is still in a
     * relatively immature development phase, this makes sense to globally track objects
     * with pending changes.
     * <br /><br />
     * In the future, each STATE in the state machine implementation would instead maintain
     * its own Publisher instance alongside the global app context's instance.
     */
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
