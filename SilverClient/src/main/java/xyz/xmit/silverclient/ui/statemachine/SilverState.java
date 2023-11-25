package xyz.xmit.silverclient.ui.statemachine;

public interface SilverState
{
    public SilverApplicationContext getParentContext();

    public void onAuthenticate();

    public void onPopup();

    public void onHome();

    public void onManageUsers();

    public void onManageItems();

    public void onLogout() throws SilverStateException;
}
