package xyz.xmit.silverclient.ui.statemachine;

import javafx.scene.Node;

public final class HomeSilverState
    extends BaseContainerSilverState
{
    private boolean hasLoadedServerData = false;

    public HomeSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
    {
        super(parentContext, containerNode, sourceEventNode);
    }

    @Override
    protected void onLoadContainer()
    {
        if (this.hasLoadedServerData) return;

        // load data

        this.hasLoadedServerData = true;
    }

    @Override
    public void onAuthenticate() {}

    @Override
    public void onPopup() {}

    @Override
    public void onHome() {}

    @Override
    public void onManageUsers() {
        this.getParentContext().setCurrentState(ManageUsersSilverState.class);
    }

    @Override
    public void onManageItems()
    {
        this.getParentContext().setCurrentState(ManageItemsSilverState.class);
    }

    @Override
    public void onLogout()
    {
    }
}
