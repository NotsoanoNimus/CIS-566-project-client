package xyz.xmit.silverclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import xyz.xmit.silverclient.api.ApiAuthenticationContext;
import xyz.xmit.silverclient.api.HttpApiClient;

import java.io.IOException;

public class SilverLibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(SilverLibraryApplication.class.getResource("authentication-window.fxml"));

        var scene = new Scene(fxmlLoader.load(), 600, 400);

        scene.getStylesheets().add(SilverLibraryApplication.class.getResource("global.css").toExternalForm());

        stage.setTitle("Silver Library Management | Log In");
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}