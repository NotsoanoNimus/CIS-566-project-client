package xyz.xmit.silverclient.ui.statemachine;

import javafx.scene.Node;

public final class ManageUsersSilverState
    extends BaseContainerSilverState
{
    public ManageUsersSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
    {
        super(parentContext, containerNode, sourceEventNode);
    }

    @Override
    protected void onLoadContainer() {
    }

    @Override
    public void onAuthenticate() {
    }

    @Override
    public void onPopup() {
    }

    @Override
    public void onRefreshData() {
    }

    @Override
    public void onHome()
    {
        this.getParentContext().setCurrentState(HomeSilverState.class);
    }

    @Override
    public void onManageUsers()
    {
    }

    @Override
    public void onManageItems()
    {
        this.getParentContext().setCurrentState(ManageItemsSilverState.class);
    }

    @Override
    public void onLogout() throws SilverStateException
    {
    }
}
