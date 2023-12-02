package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import xyz.xmit.silverclient.models.Person;
import xyz.xmit.silverclient.observer.SilverPublisher;
import xyz.xmit.silverclient.utilities.SilverUtilities;

public final class PersonDetailController
    extends HookedController
{
    private Person person;

    private Person editedPerson;

    @FXML
    public Button bClose;

    @FXML
    public Button bEditPerson;

    @FXML
    public Group gViewing;

    @FXML
    public Group gEditing;

    @FXML
    public Button bSaveNow;

    @FXML
    public Button bSaveLater;

    @FXML
    public Button bEditCancel;

    @Override
    public void controllerEntryHook()
    {
    }

    public void setPersonInstance(Person instance)
    {
        this.person = instance;
    }

    public Person getPersonInstance()
    {
        return this.person;
    }

    private void closeScene()
    {
        this.context.getCurrentState().onRevertState();

        ((Stage)((Node)this.bClose).getScene().getWindow()).close();
    }

    private void toggleEditMode(boolean isEditing)
    {
        if (isEditing) {
            this.gEditing.setVisible(true);
            this.gViewing.setVisible(false);

            this.editedPerson = this.person.clone();
        } else {
            this.gEditing.setVisible(false);
            this.gViewing.setVisible(true);

            this.editedPerson = null;
        }
    }

    public void doClose()
    {
        this.closeScene();
    }

    public void doEdit()
    {
        if (SilverPublisher.getInstance().isUniqueModelAlreadySubscribed(this.person, Person.class)) {
            var resp = SilverUtilities.ShowAlertYesNoConfirmation(
                    "You already have a pending model change for this entity. If you edit this model, you will lose those changes. Are you sure?",
                    "Proceed to Edit?");

            if (resp != ButtonType.YES) return;
        }

        SilverPublisher.getInstance().unsubscribeUniqueModelById(this.person, Person.class);

        this.toggleEditMode(true);
    }

    public void doEditCancel()
    {
        var resp = SilverUtilities.ShowAlertYesNoConfirmation(
                "Are you sure? You will lose any unsaved changes",
                "Cancel Edit");

        if (resp != ButtonType.YES) return;

        this.toggleEditMode(false);
    }

    public void doSave()
    {
        this.editedPerson.commit();

        SilverUtilities.ShowAlert(this.editedPerson.getDisplayName() + " was saved successfully!", "Success!", Alert.AlertType.INFORMATION);

        this.closeScene();
    }

    public void doSubscribe()
    {
        // Clone the edited person object so the reference to 'editedPerson' does not need to remain.
        if (!SilverPublisher.getInstance().isUniqueModelAlreadySubscribed(this.editedPerson, Person.class)) {
            SilverPublisher.getInstance().subscribe(this.editedPerson.clone());
        }

        this.closeScene();
    }
}
