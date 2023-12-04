package xyz.xmit.silverclient.ui.statemachine;

import javafx.scene.Node;
import xyz.xmit.silverclient.api.ApiFacade;
import xyz.xmit.silverclient.api.response.TitlesList;

public final class ManageItemsSilverState
    extends BaseContainerSilverState
{
    public ManageItemsSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
    {
        super(parentContext, containerNode, sourceEventNode);
    }

    @Override
    protected void onLoadContainer()
    {
    }

    @Override
    public void onRefreshData()
    {
        var titles = ApiFacade.fetchModelList("dashboard?specific=titles", TitlesList.class);

        assert titles != null;
        this.getParentContext().setTitles(titles.getData().titles);

        this.getParentContext().getController().tfSearchTitles.setText("");
        this.getParentContext().getController().onKeyFilterTitles(null);
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
    public void onLogout() throws SilverStateException
    {
    }

    @Override
    public void onRevertState()
    {
    }
}
