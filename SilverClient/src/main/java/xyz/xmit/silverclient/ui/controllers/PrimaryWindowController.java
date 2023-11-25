package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    public Pane manageUsersPane;

    @FXML
    public Pane manageItemsPane;

    @Override
    public void controllerEntryHook()
    {
        this.context = new SilverApplicationContext(this);

        this.context.getCurrentState().onHome();
    }

    public void doHome(MouseEvent mouseEvent)
    {
        this.context.getCurrentState().onHome();

        if (this.context.getCurrentState() instanceof HomeSilverState) {
            this.homeButton.getStyleClass().add("active");
            this.manageItemsButton.getStyleClass().remove("menu-active");
        }
    }

    public void doManageUsers()
    {
        this.context.getCurrentState().onManageUsers();
    }

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
