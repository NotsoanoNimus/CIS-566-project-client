package xyz.xmit.silverclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import xyz.xmit.silverclient.SilverLibraryApplication;
import xyz.xmit.silverclient.api.ApiAuthenticationContext;
import xyz.xmit.silverclient.api.HttpApiClient;
import xyz.xmit.silverclient.api.request.AuthorRequest;

public final class AuthenticationController
{
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

    private void toggleFormLock(boolean isLocked)
    {
        this.bLogin.setDisable(isLocked);
        this.tfUsername.setDisable(isLocked);
        this.tfPassword.setDisable(isLocked);
    }

    @FXML
    public void tryLogin()
    {
        this.toggleFormLock(true);

        try {
            var apiResponse = HttpApiClient.getInstance().tryLogin(
                    this.tfUsername.getText().trim().toLowerCase(), this.tfPassword.getText());

            this.lLoginStatus.setTextFill(apiResponse.getSuccess() ? Color.GREEN : Color.DARKRED);
            this.lLoginStatus.setText(apiResponse.getData());
        } catch (Exception ex) {
            //
        }

        this.toggleFormLock(false);
    }

    @FXML
    public void submitLoginOnEnter(KeyEvent e)
    {
        if (e.getCode() == KeyCode.ENTER) {
            this.tryLogin();
        }
    }
}
