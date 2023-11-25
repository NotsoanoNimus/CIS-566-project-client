package xyz.xmit.silverclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import javafx.application.Application;
import javafx.stage.Stage;
import xyz.xmit.silverclient.models.Author;
import xyz.xmit.silverclient.utilities.FxmlSceneBuilder;
import xyz.xmit.silverclient.utilities.SilverUtilities;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

public final class SilverLibraryApplication extends Application
{
    public static String getTargetSilverServerHostname()
    {
        return "localhost";
    }

    @Override
    public void start(Stage stage) {
        var a = new Author();
        a.name = "TEST AUTHOR";
        a.deleted_at = new Date();
        a.created_at = new Date();
        a.updated_at = new Date();
        a.id = UUID.randomUUID();

        try {
            System.out.println(new ObjectMapper().writeValueAsString(a));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        var inputString = "{\"id\":\"1d303998-3af6-46a6-ba26-98bd6be2d935\",\"created_at\":\"2023-11-25 04:20:31\",\"updated_at\":\"2023-11-25 04:20:31\",\"name\":\"OP OP OP OP OP\",\"deleted_at\":\"2023-11-25 04:20:31\"}";
        try {
            var aIn = new ObjectMapper().readValue(inputString, Author.class);
            System.out.println(new ObjectMapper().writeValueAsString(aIn));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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

    public static void main(String[] args) {
        Application.launch();
    }
}