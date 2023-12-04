package xyz.xmit.silverclient.ui.statemachine;

import javafx.scene.Cursor;
import javafx.scene.Node;
import xyz.xmit.silverclient.api.ApiFacade;
import xyz.xmit.silverclient.api.response.InstancesList;

public final class HomeSilverState
    extends BaseContainerSilverState
{
    public HomeSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
    {
        super(parentContext, containerNode, sourceEventNode);
    }

    @Override
    protected void onLoadContainer()
    {
        if (this.getParentContext().getDashboardData() != null) return;

        this.getParentContext().getController().tableHomeLabel.setText("Loading...");

        var apiResponse = ApiFacade.loadDashboard();

        assert apiResponse != null;
        var responseData = apiResponse.getData();

        this.getAssociatedContainerNode().setCursor(Cursor.DEFAULT);
        this.getParentContext().getController().tableHomeLabel.setText("No results");

        this.getParentContext().setDashboardData(responseData);
    }

    @Override
    public void onRefreshData()
    {
        var instances = ApiFacade.fetchModelList("dashboard?specific=instances", InstancesList.class);

        assert instances != null;
        this.getParentContext().setInstances(instances.getData().instances);

        this.getParentContext().getController().tfSearchHome.setText("");
        this.getParentContext().getController().onKeyFilterDashboard(null);
    }

    @Override
    public void onHome()
    {
        // Do nothing: we're already in the Home state.
    }

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

    @Override
    public void onRevertState()
    {
    }
}
