package xyz.xmit.silverclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import xyz.xmit.silverclient.api.ApiAuthenticationContext;
import xyz.xmit.silverclient.api.HttpApiClient;
import xyz.xmit.silverclient.api.request.AuthorRequest;

public class AuthenticationController
{
    @FXML
    private Button bButton;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Button bLogin;

    @FXML
    protected void TryDelete()
    {
        try {
            System.out.println("Deleting");
            HttpApiClient.getInstance().DeleteAsync(new AuthorRequest());
            System.out.println("OK");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    protected void tryLogin()
    {
        HttpApiClient.getInstance().tryLogin(
                this.tfUsername.getText().trim().toLowerCase(), this.tfPassword.getText());

    }
}
