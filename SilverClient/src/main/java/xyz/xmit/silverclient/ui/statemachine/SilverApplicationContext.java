package xyz.xmit.silverclient.ui.statemachine;

import xyz.xmit.silverclient.models.HomeScreenData;
import xyz.xmit.silverclient.observer.SilverPublisher;
import xyz.xmit.silverclient.ui.controllers.PrimaryWindowController;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class SilverApplicationContext
{
    private final PrimaryWindowController primaryWindowController;

    private SilverState currentState;

    private final Map<Type, SilverState> stateMap;

    private HomeScreenData dashboardData;

    public SilverApplicationContext(PrimaryWindowController primaryWindowController)
    {
        this.primaryWindowController = primaryWindowController;

        // Create an in-memory mapping of all application states to track throughout the lifetime of the app context.
        this.stateMap = new HashMap<>();
        this.stateMap.put(
                HomeSilverState.class,
                new HomeSilverState(this, primaryWindowController.homePane, primaryWindowController.homeButton));
        this.stateMap.put(
                ManageUsersSilverState.class,
                new ManageUsersSilverState(this, primaryWindowController.manageUsersPane, primaryWindowController.manageUsersButton));
        this.stateMap.put(
                ManageItemsSilverState.class,
                new ManageItemsSilverState(this, primaryWindowController.manageItemsPane, primaryWindowController.manageItemsButton));
        this.stateMap.put(
                PopupSilverState.class,
                new PopupSilverState(this));

        this.setCurrentState(HomeSilverState.class);
    }

    public <T extends Class<? extends SilverState>> void setCurrentState(T newStateType)
    {
        this.currentState = this.stateMap.get(newStateType);

        // Only set the active panel if the chosen state has a parent node/container attached to it.
        if (BaseContainerSilverState.class.isAssignableFrom(this.currentState.getClass())) {
            var assignableTypes = this.stateMap.keySet().stream().filter(
                    type -> BaseContainerSilverState.class.isAssignableFrom((Class<?>)type)).toList();

            for (var itemType : assignableTypes) {
                var asBaseContainerState = ((BaseContainerSilverState)this.stateMap.get(itemType));

                asBaseContainerState.getAssociatedContainerNode().setVisible(newStateType == itemType);

                if (newStateType == itemType) {
                    asBaseContainerState.getSourceEventNode().getStyleClass().add("menu-active");
                } else {
                    asBaseContainerState.getSourceEventNode().getStyleClass().remove("menu-active");
                }
            }

            ((BaseContainerSilverState)this.currentState).onLoadContainer();
        }
    }

    private boolean hasUnsavedChanges()
    {
        return SilverPublisher.getInstance().hasSubscribers();
    }

    public SilverState getCurrentState()
    {
        return this.currentState;
    }

    public PrimaryWindowController getController()
    {
        return this.primaryWindowController;
    }

    public HomeScreenData getDashboardData()
    {
        return this.dashboardData;
    }

    public void setDashboardData(HomeScreenData data)
    {
        this.dashboardData = data;

        this.getController().refreshDashboardData(this.dashboardData);
    }

    public void setDashboardDataFiltered(HomeScreenData data)
    {
        this.getController().refreshDashboardData(data);
    }
}
