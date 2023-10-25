package xyz.xmit.silverclient;

import javafx.application.Application;
import javafx.stage.Stage;
import xyz.xmit.silverclient.utilities.FxmlSceneBuilder;
import xyz.xmit.silverclient.utilities.SilverUtilities;

import java.io.IOException;

public class SilverLibraryApplication extends Application {
    @Override
    public void start(Stage stage) {
        // The application will ALWAYS enter on the authentication window at start-up.
        new FxmlSceneBuilder("authentication-window.fxml", stage)
                .setWidth(600)
                .setHeight(400)
                .setTitle("Silver Library Management | Log In")
                .setResizeable(false)
                .build();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}