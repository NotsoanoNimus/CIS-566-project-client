package xyz.xmit.silverclient.ui.statemachine;

import javafx.scene.Node;

public final class ManageItemsSilverState
    extends BaseContainerSilverState
{
    public ManageItemsSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
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
        this.getParentContext().setCurrentState(ManageUsersSilverState.class);
    }

    @Override
    public void onManageItems()
    {
    }

    @Override
    public void onLogout() throws SilverStateException {

    }
}
