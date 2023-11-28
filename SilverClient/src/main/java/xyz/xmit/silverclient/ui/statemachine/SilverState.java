package xyz.xmit.silverclient.ui.statemachine;

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
