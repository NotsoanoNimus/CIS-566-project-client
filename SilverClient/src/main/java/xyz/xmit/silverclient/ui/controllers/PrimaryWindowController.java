package xyz.xmit.silverclient.ui.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.xmit.silverclient.models.HomeScreenData;
import xyz.xmit.silverclient.models.InventoryItemInstance;
import xyz.xmit.silverclient.observer.SilverPublisher;
import xyz.xmit.silverclient.ui.statemachine.PopupSilverState;
import xyz.xmit.silverclient.ui.statemachine.SilverApplicationContext;
import xyz.xmit.silverclient.ui.statemachine.SilverStateException;
import xyz.xmit.silverclient.utilities.FxmlSceneBuilder;
import xyz.xmit.silverclient.utilities.SceneDirector;
import xyz.xmit.silverclient.utilities.SilverUtilities;

public final class PrimaryWindowController
    extends HookedController
{
    private SilverApplicationContext context;

    @FXML
    public Group gUnsavedChanges;

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
    public Label tableHomeLabel;

    @FXML
    public TextField tfSearchHome;

    @FXML
    public Button bRefreshHome;

    @FXML
    public Pane manageUsersPane;

    @FXML
    public Pane manageItemsPane;

    @Override
    public void controllerEntryHook()
    {
        // Other setup functionality.
        SilverUtilities.GetTableColumnByName(this.tableHome, "Item Title")
                .setCellValueFactory(new PropertyValueFactory<>("title"));

        SilverUtilities.GetTableColumnByName(this.tableHome, "Edition")
                .setCellValueFactory(new PropertyValueFactory<>("edition"));

        SilverUtilities.GetTableColumnByName(this.tableHome, "SKU#")
                .setCellValueFactory(new PropertyValueFactory<>("sku"));

        SilverUtilities.GetTableColumnByName(this.tableHome, "Status")
                .setCellValueFactory(new PropertyValueFactory<>("state"));

        SilverUtilities.GetTableColumnByName(this.tableHome, "Condition")
                .setCellValueFactory(new PropertyValueFactory<>("condition"));

        SilverUtilities.GetTableColumnByName(this.tableHome, "Acquired At")
                .setCellValueFactory(new PropertyValueFactory<>("acquiredAt"));

        this.tableHome.setRowFactory(tv -> {
            var row = new TableRow<InventoryItemInstance>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() != 2 || row.isEmpty()) return;

                // Deny multiple pop-up states. Only one pop-up should be available at a time.
                if (this.context.getCurrentState() instanceof PopupSilverState)
                {
                    return;
                }

                // Spawn a popup window and enter a pop-up state.
                var hookController = SceneDirector.constructItemInstancePopupWindow(row.getItem(), this.context);

                ((ItemInstanceDetailController)hookController).setInventoryItemInstance(row.getItem());

                var previousState = this.context.getCurrentState().getClass();
                this.context.setCurrentState(PopupSilverState.class);
                ((PopupSilverState)this.context.getCurrentState()).setPreviousStateClass(previousState);
            });

            return row;
        });

        // Create a new context and immediately call 'on-home' to load data and display dashboard data.
        this.context = new SilverApplicationContext(this);

        this.tableHome.setCursor(Cursor.DEFAULT);
    }

    public void refreshDashboardData(HomeScreenData dashboardData)
    {
        if (dashboardData == null) return;

        this.tableHome.getItems().setAll(dashboardData.instances);
    }

    /**
     * Capture key-press events on the Home dashboard for filtering the list of instances.
     *
     * @param e The key-press event.
     */
    public void onKeyFilterDashboard(KeyEvent e)
    {
        e.consume();

        if (this.tfSearchHome.getText().isEmpty()) {
            this.context.setDashboardDataFiltered(this.context.getDashboardData());
            return;
        }

        // First try to parse the input as a numeric barcode SKU.
        try {
            var parsedSku = Integer.parseUnsignedInt(this.tfSearchHome.getText());

            var filteredSet = this.context.getDashboardData().instances.stream().filter(
                    i -> String.valueOf(i.barcode_sku).contains(String.valueOf(parsedSku))).toList();

            var newData = new HomeScreenData();
            newData.instances = filteredSet;

            this.context.setDashboardDataFiltered(newData);
        } catch (NumberFormatException ex) {
            // If parsing a number doesn't work, just compare the string against any book titles.
            try {
                var filteredSet = this.context.getDashboardData().instances.stream().filter(
                        i -> i.getTitle().trim().toLowerCase().contains(this.tfSearchHome.getText().trim().toLowerCase())).toList();

                var newData = new HomeScreenData();
                newData.instances = filteredSet;

                this.context.setDashboardDataFiltered(newData);
            } catch (Exception innerEx) {
                // do not crash the app when there's an exception searching...
            }
        } catch (Exception ex) {
            // do nothing as well...
        }
    }

    @FXML
    public void doRefresh()
    {
        this.context.getCurrentState().onRefreshData();
    }

    @FXML
    public void doHome()
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
    public void doCommit()
    {
        SilverPublisher.getInstance().commitAll();

        this.gUnsavedChanges.setVisible(false);
    }

    @FXML
    public void doRevert()
    {
        var confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to revert? You will permanently lose all uncommitted changes.",
                ButtonType.YES,
                ButtonType.NO);

        confirmation.setTitle("Revert Changes");
        confirmation.setHeaderText("Revert Changes");
        confirmation.setResizable(false);
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            SilverPublisher.getInstance().unsubscribeAll();

            this.gUnsavedChanges.setVisible(false);
        }
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
