package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import xyz.xmit.silverclient.models.InventoryItemInstance;
import xyz.xmit.silverclient.ui.statemachine.HomeSilverState;
import xyz.xmit.silverclient.ui.statemachine.SilverApplicationContext;
import xyz.xmit.silverclient.ui.statemachine.SilverStateException;
import xyz.xmit.silverclient.utilities.SilverUtilities;

public final class PrimaryWindowController
    extends HookedController
{
    private SilverApplicationContext context;

    @FXML
    public Button homeButton;

    @FXML
    public Button manageUsersButton;

    @FXML
    public Button manageItemsButton;

    @FXML
    public VBox mainMenuContainer;

    @FXML
    public Pane homePane;

    @FXML
    public TableView<InventoryItemInstance> tableHome;

    @FXML
    public TextField tfSearchHome;

    @FXML
    public Button bSearchHome;

    @FXML
    public Pane manageUsersPane;

    @FXML
    public Pane manageItemsPane;

    @Override
    public void controllerEntryHook()
    {
        // Create a new context and immediately call 'on-home' to load data and display dashboard data.
        this.context = new SilverApplicationContext(this);

        this.context.getCurrentState().onHome();
    }

    public void refreshDashboardData()
    {
        var data = this.context.getDashboardData();
    }

    @FXML
    public void doHome(MouseEvent mouseEvent)
    {
        this.context.getCurrentState().onHome();
    }

    @FXML
    public void doManageUsers()
    {
        this.context.getCurrentState().onManageUsers();
    }

    @FXML
    public void doManageItems()
    {
        this.context.getCurrentState().onManageItems();
    }

    @FXML
    public void doLogout()
    {
        try {
            this.context.getCurrentState().onLogout();

            SilverUtilities.ShowLogoutDialog();
        } catch (SilverStateException stateDenied) {
            // consume the exception; it's only here to deny logouts
        } catch (Exception ex) {
            // consume the exception, but should probably actually do something here...
        }
    }
}
