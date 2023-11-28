package xyz.xmit.silverclient;

import javafx.application.Application;
import javafx.stage.Stage;
import xyz.xmit.silverclient.utilities.FxmlSceneBuilder;

public final class SilverLibraryApplication extends Application
{
    public static String getTargetSilverServerHostname()
    {
        var envHost = System.getenv("SILVER_TARGET_HOSTNAME");

        return envHost != null
            ? envHost
            : "localhost";
    }

    @Override
    public void start(Stage stage)
    {
        // The application will ALWAYS enter on the authentication window at start-up.
        new FxmlSceneBuilder("authentication-window.fxml", stage)
                .setWidth(600)
                .setHeight(400)
                .setTitle("Silver Library Management | Log In")
                .setUndecorated(false)
                .setResizeable(false)
                .setExitMonitoring(false)
                .build();
    }

    public static void main(String[] args)
    {
        Application.launch();
    }
}