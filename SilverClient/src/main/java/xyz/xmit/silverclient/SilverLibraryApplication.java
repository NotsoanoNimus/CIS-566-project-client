package xyz.xmit.silverclient;

import javafx.application.Application;
import javafx.stage.Stage;
import xyz.xmit.silverclient.utilities.FxmlSceneBuilder;
import xyz.xmit.silverclient.utilities.SceneDirector;

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
        SceneDirector.constructLoginWindow(stage);
    }

    public static void main(String[] args)
    {
        Application.launch();
    }
}