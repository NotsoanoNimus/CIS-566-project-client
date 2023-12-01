package xyz.xmit.silverclient.ui.statemachine;

/**
 * DESIGN PATTERN: State Machine (Behavioral)
 * <br /><br />
 * The State Machine pattern is used to control and constrain transitions between different
 * application states. It also protects the application from entering a state it shouldn't
 * be able to access while in another; for example, opening a pop-up window for editing
 * User objects while in the "Home" state (accessing the Home tab in the application).
 * <br /><br />
 * Each state has access to the parent application context, which is part of the pattern.
 * Likewise, the application context has access to a map of underlying states that can
 * transition between each other at-will. With this design, any state INSIDE the context
 * can swap the context's state to another, which is how the flow between them is easily
 * managed.
 */
public interface SilverState
{
    public SilverApplicationContext getParentContext();

    public void onRefreshData();

    public void onHome();

    public void onManageUsers();

    public void onManageItems();

    public void onLogout() throws SilverStateException;

    public void onRevertState();
}
