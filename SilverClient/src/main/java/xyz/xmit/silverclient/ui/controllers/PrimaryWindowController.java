package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import xyz.xmit.silverclient.models.InventoryItemInstance;
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

                // Spawn a popup window and enter a pop-up state.
                System.out.println("Double-click!");
            });

            return row;
        });

        // Create a new context and immediately call 'on-home' to load data and display dashboard data.
        this.context = new SilverApplicationContext(this);

        this.context.getCurrentState().onHome();

        this.tableHome.setCursor(Cursor.DEFAULT);
    }

    public void refreshDashboardData()
    {
        var data = this.context.getDashboardData();
        if (data == null) return;

        this.tableHome.getItems().setAll(data.instances);
    }

    public void onKeyFilterDashboard()
    {
        try {
            var parsedSku = Integer.parseUnsignedInt(this.tfSearchHome.getText());

        } catch (Exception ex) {
            // do nothing...
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
