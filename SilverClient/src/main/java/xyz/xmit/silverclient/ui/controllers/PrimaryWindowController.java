package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public final class PrimaryWindowController {
    @FXML
    public VBox mainMenuContainer;

    @FXML
    public Pane homePane;

    @FXML
    public Pane manageUsersPane;

    @FXML
    public Pane manageItemsPane;

    @FXML
    public void doHome()
    {
    }

    @FXML
    public void doManageUsers()
    {
    }

    @FXML
    public void doManageItems()
    {
    }

    @FXML
    public void doLogout()
    {
        var confirmationOfExit = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit? Unsaved changes will not be committed.",
                ButtonType.YES,
                ButtonType.CANCEL);

        confirmationOfExit.setHeaderText("Log Out");
        confirmationOfExit.setTitle("Log Out");
        confirmationOfExit.setResizable(false);
        confirmationOfExit.showAndWait();

        if (confirmationOfExit.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }
}
