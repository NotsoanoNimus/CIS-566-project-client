package xyz.xmit.silverclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.xmit.silverclient.api.ApiAuthenticationContext;
import xyz.xmit.silverclient.api.HttpApiClient;

import java.io.IOException;

public class SilverLibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SilverLibraryApplication.class.getResource("authentication-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Silver Library Management | Log In");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        HttpApiClient.getInstance().setAuthenticationContext(
                new ApiAuthenticationContext("admin@bookwarehouse.io", "admin"));

        launch();
    }
}