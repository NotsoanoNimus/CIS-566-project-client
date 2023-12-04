package xyz.xmit.silverclient.ui.statemachine;

import javafx.scene.Node;
import xyz.xmit.silverclient.api.ApiFacade;
import xyz.xmit.silverclient.api.response.PeopleList;

public final class ManageUsersSilverState
    extends BaseContainerSilverState
{
    public ManageUsersSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
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
        var people = ApiFacade.fetchModelList("dashboard?specific=people", PeopleList.class);

        assert people != null;
        this.getParentContext().setPeople(people.getData().people);

        this.getParentContext().getController().tfSearchManageUsers.setText("");
        this.getParentContext().getController().onKeyFilterUsers(null);
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
    public void onLogout()
    {
    }

    @Override
    public void onRevertState()
    {
    }
}
