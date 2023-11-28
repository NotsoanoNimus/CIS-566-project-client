package xyz.xmit.silverclient.ui.statemachine;

import javafx.scene.Node;
import xyz.xmit.silverclient.api.ApiFacade;
import xyz.xmit.silverclient.models.HomeScreenData;
import xyz.xmit.silverclient.utilities.SilverUtilities;

public final class HomeSilverState
    extends BaseContainerSilverState
{
    private HomeScreenData dashboardData = null;

    public HomeSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
    {
        super(parentContext, containerNode, sourceEventNode);
    }

    @Override
    protected void onLoadContainer()
    {
        if (this.dashboardData != null) return;

        // load data
        var apiResponse = ApiFacade.loadDashboard();
        if (apiResponse == null || apiResponse.getData() == null) {
            SilverUtilities.ShowAlert("Failed to properly parse or download dashboard data from the server.", "Dashboard Error", true);
        }

        assert apiResponse != null;

        this.dashboardData = apiResponse.getData();
    }

    @Override
    public void onAuthenticate() {}

    @Override
    public void onPopup() {}

    @Override
    public void onHome()
    {
        this.getParentContext().setDashboardData(this.dashboardData);
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
}
