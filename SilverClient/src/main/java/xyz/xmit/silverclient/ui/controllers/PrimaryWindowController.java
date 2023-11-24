package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class PrimaryWindowController {
    @FXML
    public void doLogout()
    {
        var confirmationOfExit = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit? Unsaved changes will not be committed.",
                ButtonType.YES,
                ButtonType.CANCEL);

        confirmationOfExit.showAndWait();

        if (confirmationOfExit.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }
}
