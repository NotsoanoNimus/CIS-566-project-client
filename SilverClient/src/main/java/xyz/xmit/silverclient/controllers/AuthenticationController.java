package xyz.xmit.silverclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import xyz.xmit.silverclient.api.ApiAuthenticationContext;
import xyz.xmit.silverclient.api.HttpApiClient;
import xyz.xmit.silverclient.api.request.AuthorRequest;

public class AuthenticationController
{
    @FXML
    private Button bButton;

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
}
