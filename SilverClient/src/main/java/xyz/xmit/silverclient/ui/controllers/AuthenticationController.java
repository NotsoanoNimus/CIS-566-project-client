package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import xyz.xmit.silverclient.utilities.FxmlSceneBuilder;
import xyz.xmit.silverclient.utilities.SilverUtilities;
import xyz.xmit.silverclient.api.HttpApiClient;

public final class AuthenticationController
{
    @FXML
    private AnchorPane apAuth;

    @FXML
    private ImageView logoImage;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Button bLogin;

    @FXML
    private Label lLoginStatus;

    /**
     * Toggle locking on select form components from the FXML resource bound to this controller.
     * @param isLocked Whether the items should be un/locked.
     */
    private void toggleFormLock(boolean isLocked)
    {
        this.logoImage.setDisable(isLocked);
        this.bLogin.setDisable(isLocked);
        this.tfUsername.setDisable(isLocked);
        this.tfPassword.setDisable(isLocked);
    }

    /**
     * Attempt to log into the remote Library Management System with the given credentials.
     */
    @FXML
    public void tryLogin()
    {
        this.toggleFormLock(true);

        try {
            // Attempt to contact the API with the given username/password combination.
            var apiResponse = HttpApiClient.getInstance().tryLogin(
                    this.tfUsername.getText().trim().toLowerCase(), this.tfPassword.getText());

            // Set the response content and color based on the API response.
            this.lLoginStatus.setTextFill(apiResponse.getSuccess() ? Color.GREEN : Color.DARKRED);
            this.lLoginStatus.setText(apiResponse.getData());

            // Get the current Stage reference.
            var stageReference = (Stage)this.apAuth.getScene().getWindow();

            // On request success, asynchronously pause for ~2.5 seconds then swap to the primary app scene.
            if (apiResponse.getSuccess()) {
                SilverUtilities.RunDelayedEvent(
                        2.5,
                        event -> new FxmlSceneBuilder("primary-window.fxml", stageReference)
                                    .setWidth(1000)
                                    .setHeight(750)
                                    .setTitle("Silver Library Management | Dashboard")
                                    .setResizeable(false)
                                    .setUndecorated(false)
                                    .buildWithBaseControllerAction(PrimaryWindowController.class));

                return;
            }
        } catch (Exception ex) {
            var loginIssueAlert = new Alert(
                    Alert.AlertType.WARNING,
                    "There was an issue attempting to log in.",
                    ButtonType.OK);

            loginIssueAlert.showAndWait();
        }

        this.toggleFormLock(false);
    }

    /**
     * Event handler to watch the form text fields for the ENTER key. When detected, it will
     * automatically call the {@link #tryLogin()} method to submit a login request.
     * @param e The event to handle; contains the key-code struck.
     */
    @FXML
    public void submitLoginOnEnter(KeyEvent e)
    {
        if (e.getCode() == KeyCode.ENTER) {
            this.tryLogin();
        }
    }
}
