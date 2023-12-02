package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import xyz.xmit.silverclient.models.Person;

public final class PersonDetailController
    extends HookedController
{
    private Person person;

    @FXML
    public Button bClose;

    @Override
    public void controllerEntryHook()
    {
    }

    public void setPersonInstance(Person instance)
    {
        this.person = instance;
    }

    public void doClose(MouseEvent event)
    {
        this.context.getCurrentState().onRevertState();

        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
