package xyz.xmit.silverclient.utilities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

public final class SilverUtilities
{
    /**
     * Run an event via an FX Timeline instance, to asynchronously fire an event after a time-delay
     * WITHOUT interrupting the main application Thread(s).
     * @param delay Amount of time to wait before running the event delegate.
     * @param eventHandler The event delegate to run when the expressed delay has elapsed.
     */
    public static void RunDelayedEvent(double delay, EventHandler<ActionEvent> eventHandler)
    {
        var timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(delay),
                        eventHandler
                ));

        timer.play();
    }

    public static boolean ShowLogoutDialog()
    {
        var confirmationOfExit = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit? Unsaved changes will not be committed.",
                ButtonType.YES,
                ButtonType.CANCEL);

        confirmationOfExit.setHeaderText("Log Out");
        confirmationOfExit.setTitle("Log Out");
        confirmationOfExit.setResizable(false);
        confirmationOfExit.showAndWait();

        if (confirmationOfExit.getResult() == ButtonType.YES) {
            System.exit(0);
        }

        return false;
    }
}
