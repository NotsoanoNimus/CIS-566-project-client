package xyz.xmit.silverclient.utilities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import xyz.xmit.silverclient.observer.SilverPublisher;

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

    public static ButtonType ShowAlertYesNoConfirmation(String message, String header)
    {
        var confirmationOfExit = new Alert(
                Alert.AlertType.CONFIRMATION,
                message,
                ButtonType.YES,
                ButtonType.NO,
                ButtonType.CANCEL);

        confirmationOfExit.setHeaderText(header);
        confirmationOfExit.setTitle(header);
        confirmationOfExit.setResizable(false);
        confirmationOfExit.showAndWait();

        return confirmationOfExit.getResult();
    }

    public static boolean ShowLogoutDialog()
    {
        if (SilverPublisher.getInstance().getSubscribers().size() > 1) {
            var res = ShowAlertYesNoConfirmation("You are about to log out. Would you like to commit your unsaved changes?", "Log Out");

            if (res == ButtonType.CANCEL) {
                return false;
            } else if (res == ButtonType.YES) {
                SilverPublisher.getInstance().commitAll();

                return true;
            }

            System.exit(0);
        } else {
            var res = ShowAlertYesNoConfirmation("Are you sure you want to log out?", "Log Out");

            if (res == ButtonType.YES) {
                System.exit(0);
            }
        }

        return false;
    }

    public static void ShowAlert(String message, String header)
    {
        ShowAlert(message, header, false);
    }

    public static void ShowAlert(String message, String header, Alert.AlertType type)
    {
        ShowAlert(message, header, type, false);
    }

    public static void ShowAlert(String message, String header, boolean exits)
    {
        ShowAlert(message, header, Alert.AlertType.ERROR, exits);
    }

    public static void ShowAlert(String message, String header, Alert.AlertType type, boolean exits)
    {
        var alert = new Alert(type, message, ButtonType.OK);

        alert.setHeaderText(header);
        alert.setTitle("Silver Alert");
        alert.setResizable(false);

        if (exits) {
            alert.setOnHidden(evt -> Platform.exit());
        }

        try {
            alert.show();
        } catch (Exception ex) {
            ex.printStackTrace();

            if (exits) {
                System.exit(0);
            }
        }
    }

    public static <T> TableColumn<T, ?> GetTableColumnByName(TableView<T> tableView, String name)
    {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equalsIgnoreCase(name)) return col;
        return null;
    }
}
