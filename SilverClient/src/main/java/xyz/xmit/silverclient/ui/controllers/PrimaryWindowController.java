package xyz.xmit.silverclient.ui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import xyz.xmit.silverclient.api.ApiFacade;
import xyz.xmit.silverclient.api.HttpApiClient;
import xyz.xmit.silverclient.models.HomeScreenData;
import xyz.xmit.silverclient.models.InventoryItem;
import xyz.xmit.silverclient.models.InventoryItemInstance;
import xyz.xmit.silverclient.models.Person;
import xyz.xmit.silverclient.observer.SilverPublisher;
import xyz.xmit.silverclient.ui.statemachine.PopupSilverState;
import xyz.xmit.silverclient.ui.statemachine.SilverApplicationContext;
import xyz.xmit.silverclient.ui.statemachine.SilverStateException;
import xyz.xmit.silverclient.utilities.SceneDirector;
import xyz.xmit.silverclient.utilities.SilverUtilities;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

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
    public Pane manageUsersPane;

    @FXML
    public TableView<Person> tablePeople;

    @FXML
    public TextField tfSearchManageUsers;

    private Person selectedPerson;

    @FXML
    public Button bPersonCheckouts;

    @FXML
    public Button bPersonReset;

    @FXML
    public Button bPersonBan;

    @FXML
    public Pane manageItemsPane;

    @FXML
    public TableView<InventoryItem> tableItems;

    @FXML
    public TextField tfSearchTitles;

    @FXML
    public AnchorPane apSelectedTitle;

    @Override
    public void controllerEntryHook()
    {
        // Dynamically configure tables to accept data and handle other actions.
        this.registerTables();

        // Create a new context and immediately call 'on-home' to load data and display dashboard data.
        this.context = new SilverApplicationContext(this);

        this.tableHome.setCursor(Cursor.DEFAULT);
    }

    @FXML
    public void doRefresh()
    {
        this.selectedPerson = null;
        this.bPersonCheckouts.setDisable(true);
        this.bPersonReset.setDisable(true);
        this.bPersonBan.setDisable(true);

        if (SilverPublisher.getInstance().hasSubscribers()) {
            var res = SilverUtilities.ShowAlertYesNoConfirmation(
                    "You have pending changes which will be overwritten by a data refresh. Are you sure?",
                    "Refresh");

            if (res == ButtonType.NO || res == ButtonType.CANCEL) {
                return;
            }

            SilverPublisher.getInstance().unsubscribeAll();
        }

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
        var confirmation = SilverUtilities.ShowAlertYesNoConfirmation(
                "Are you sure you want to commit all changes? This might take a moment.",
                "Submit Changes");

        if (confirmation == ButtonType.YES) {
            SilverPublisher.getInstance().commitAll();

            this.gUnsavedChanges.setVisible(false);

            this.context.getCurrentState().onRefreshData();
        }
    }

    @FXML
    public void doRevert()
    {
        var confirmation = SilverUtilities.ShowAlertYesNoConfirmation(
                "Are you sure you want to revert? You will permanently lose all uncommitted changes.",
                "Revert Changes");

        if (confirmation == ButtonType.YES) {
            SilverPublisher.getInstance().unsubscribeAll();

            this.gUnsavedChanges.setVisible(false);

            this.context.getCurrentState().onRefreshData();
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

    @FXML
    public void doPersonCheckouts()
    {
    }

    @FXML
    public void doPersonResetPassword()
    {
    }

    @FXML
    public void doPersonBan()
    {
        if (this.selectedPerson == null) return;

        var response =
                this.selectedPerson.isUserBanned()
                        ? SilverUtilities.ShowAlertYesNoConfirmation("Are you sure? This person will be unbanned and regain access to the system.", "Unban User")
                        : SilverUtilities.ShowAlertYesNoConfirmation("Are you sure? This person will be banned and lose access to the system.", "Ban User");

        if (response != ButtonType.YES) return;

        if (!this.selectedPerson.isUserBanned()) {
            ApiFacade.safeApiRequest("DELETE", this.selectedPerson, Person.class, true);
        } else {
            ApiFacade.unbanPerson(this.selectedPerson);
        }

        this.doRefresh();
    }

    public void refreshDashboardData(HomeScreenData dashboardData)
    {
        if (dashboardData == null) return;

        if (this.tableHome.getItems() != null) this.tableHome.getItems().setAll(dashboardData.instances);
        if (this.tablePeople.getItems() != null) this.tablePeople.getItems().setAll(dashboardData.people);
        if (this.tableItems.getItems() != null) this.tableItems.getItems().setAll(dashboardData.titles);
    }

    public void flagPendingChanges()
    {
        this.gUnsavedChanges.setVisible(true);
    }

    /**
     * Capture key-press events on the Home dashboard for filtering the list of instances.
     *
     * @param e The key-press event.
     */
    public void onKeyFilterDashboard(KeyEvent e)
    {
        if (e != null) e.consume();

        if (this.tfSearchHome.getText() != null && this.tfSearchHome.getText().isEmpty()) {
            this.context.setDashboardDataFiltered(this.context.getDashboardData());
            return;
        }

        // First try to parse the input as a numeric barcode SKU.
        try {
            var parsedSku = Integer.parseUnsignedInt(this.tfSearchHome.getText());

            var filteredSet = this.context.getDashboardData().instances.stream().filter(
                    i -> String.valueOf(i.barcode_sku).contains(String.valueOf(parsedSku))).toList();

            var newData = this.context.getDashboardData().createNewFromThis();
            newData.instances = filteredSet;

            this.context.setDashboardDataFiltered(newData);
        } catch (NumberFormatException ex) {
            // If parsing a number doesn't work, just compare the string against any book titles.
            try {
                var filteredSet = this.context.getDashboardData().instances.stream().filter(
                        i -> i.getTitle().trim().toLowerCase().contains(this.tfSearchHome.getText().trim().toLowerCase())).toList();

                var newData = this.context.getDashboardData().createNewFromThis();
                newData.instances = filteredSet;

                this.context.setDashboardDataFiltered(newData);
            } catch (Exception innerEx) {
                // do not crash the app when there's an exception searching...
            }
        } catch (Exception ex) {
            // do nothing as well...
        }
    }

    public void onKeyFilterUsers(KeyEvent e)
    {
        if (e != null) e.consume();

        if (this.tfSearchManageUsers.getText() != null && this.tfSearchManageUsers.getText().isEmpty()) {
            this.context.setDashboardDataFiltered(this.context.getDashboardData());
            return;
        }

        // First try to parse the input as a numeric barcode SKU.
        try {
            var searchText = this.tfSearchManageUsers.getText().toLowerCase();

            var filteredSet = this.context.getDashboardData().people.stream()
                    .filter(
                            p -> p.getDisplayName().toLowerCase().contains(searchText)
                                    || p.getEmail().toLowerCase().contains(searchText)
                                    || p.getIdentifier().toLowerCase().contains(searchText)
                    )
                    .toList();

            var newData = this.context.getDashboardData().createNewFromThis();
            newData.people = filteredSet;

            this.context.setDashboardDataFiltered(newData);
        } catch (Exception ex) {
            // do nothing as well...
        }
    }

    public void onKeyFilterTitles(KeyEvent e)
    {
        if (e != null) e.consume();

        if (this.tfSearchTitles.getText() != null && this.tfSearchTitles.getText().isEmpty()) {
            this.context.setDashboardDataFiltered(this.context.getDashboardData());
            return;
        }

        // First try to parse the input as a numeric barcode SKU.
        try {
            var searchText = this.tfSearchTitles.getText().toLowerCase();

            var filteredSet = this.context.getDashboardData().titles.stream()
                    .filter(
                            i -> i.title.toLowerCase().contains(searchText)
                                    || !i.authors.stream().filter(a -> a.name.toLowerCase().contains(searchText)).toList().isEmpty()
                    )
                    .toList();

            var newData = this.context.getDashboardData().createNewFromThis();
            newData.titles = filteredSet;

            this.context.setDashboardDataFiltered(newData);
        } catch (NumberFormatException ex) {
            // do nothing...
        }
    }

    private void registerTables()
    {
        // HOME TABLE
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

        ((TableColumn<InventoryItemInstance, String>)SilverUtilities.GetTableColumnByName(this.tableHome, "Acquired At"))
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().acquired_at.toString()));

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

        // ======================
        // MANAGE USERS TABLE
        // ======================
        SilverUtilities.GetTableColumnByName(this.tablePeople, "Name")
                .setCellValueFactory(new PropertyValueFactory<>("displayName"));

        SilverUtilities.GetTableColumnByName(this.tablePeople, "Email")
                .setCellValueFactory(new PropertyValueFactory<>("email"));

        SilverUtilities.GetTableColumnByName(this.tablePeople, "Identifier")
                .setCellValueFactory(new PropertyValueFactory<>("identifier"));

        SilverUtilities.GetTableColumnByName(this.tablePeople, "Location")
                .setCellValueFactory(new PropertyValueFactory<>("location"));

        SilverUtilities.GetTableColumnByName(this.tablePeople, "Status")
                .setCellValueFactory(new PropertyValueFactory<>("status"));

        this.tablePeople.setRowFactory(tv -> {
            var row = new TableRow<Person>() {
                @Override
                protected void updateItem(Person p, boolean b) {
                    super.updateItem(p, b);

                    if (p == null) return;

                    getStyleClass().removeAll(Collections.singleton("person-row-banned"));
                    getStyleClass().removeAll(Collections.singleton("person-row-staff"));
                    getStyleClass().removeAll(Collections.singleton("person-row-self"));

                    if (p.isUserBanned()) {
                        getStyleClass().add("person-row-banned");
                    } else if (p.isUserSelf()) {
                        getStyleClass().add("person-row-self");
                    } else if (p.isUserStaff()) {
                        getStyleClass().add("person-row-staff");
                    }
                }
            };

            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) return;

                // Deny multiple pop-up states. Only one pop-up should be available at a time.
                if (this.context.getCurrentState() instanceof PopupSilverState)
                {
                    return;
                }

                if (event.getClickCount() >= 2) {
                    // Users cannot edit themselves.
                    if (row.getItem().isUserSelf()) {
                        SilverUtilities.ShowAlert("You cannot edit your own profile.", "Access Denied");

                        return;
                    }

                    // Spawn a popup window and enter a pop-up state.
                    SceneDirector.constructPersonPopupWindow(row.getItem(), this.context);

                    var previousState = this.context.getCurrentState().getClass();
                    this.context.setCurrentState(PopupSilverState.class);
                    ((PopupSilverState) this.context.getCurrentState()).setPreviousStateClass(previousState);
                } else if (event.getClickCount() == 1) {
                    // Users cannot edit themselves.
                    if (row.getItem().isUserSelf()) {
                        this.selectedPerson = null;

                        this.bPersonCheckouts.setDisable(true);
                        this.bPersonReset.setDisable(true);
                        this.bPersonBan.setDisable(true);

                        return;
                    }

                    this.selectedPerson = row.getItem();

                    this.bPersonCheckouts.setDisable(false);
                    this.bPersonReset.setDisable(false);
                    this.bPersonBan.setDisable(false);
                }
            });

            return row;
        });

        // ======================
        // MANAGE TITLES TABLE
        // ======================
        SilverUtilities.GetTableColumnByName(this.tableItems, "Title")
                .setCellValueFactory(new PropertyValueFactory<>("title"));

        ((TableColumn<InventoryItem, String>)
                SilverUtilities.GetTableColumnByName(this.tableItems, "Author(s)"))
                .setCellValueFactory(cellData -> {
                    var authors = String.join(", ", cellData.getValue().authors.stream().map(a -> a.name).toList());
                    return new SimpleStringProperty(authors);
                });

        this.tableItems.setRowFactory(tv -> {
            var row = new TableRow<InventoryItem>();
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) return;

                // Display the details of the row in the right-side panel.
            });

            return row;
        });
    }
}
