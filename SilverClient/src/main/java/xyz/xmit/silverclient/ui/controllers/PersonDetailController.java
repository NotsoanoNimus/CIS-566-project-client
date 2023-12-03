package xyz.xmit.silverclient.ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import xyz.xmit.silverclient.models.Person;
import xyz.xmit.silverclient.observer.SilverPublisher;
import xyz.xmit.silverclient.utilities.SilverUtilities;

public final class PersonDetailController
    extends HookedController
{
    private Person person;

    private Person editedPerson;

    @FXML public Button bClose;

    @FXML public Button bEditPerson;

    @FXML public Group gViewing;

    @FXML public Group gEditing;

    @FXML public Button bSaveNow;

    @FXML public Button bSaveLater;

    @FXML public Button bEditCancel;

    @FXML public Label labelUncommittedChanges;

    @FXML public TextField tfBarcode;

    @FXML public TextField tfFirstName;

    @FXML public TextField tfLastName;

    @FXML public TextField tfMiddleName;

    @FXML public TextField tfAddressLine1;

    @FXML public TextField tfAddressLine2;

    @FXML public TextField tfCity;

    @FXML public TextField tfState;

    @FXML public TextField tfZip;

    @FXML public CheckBox cbIsStaff;

    @FXML public CheckBox cbHeadOfFamily;

    @Override
    public void controllerEntryHook()
    {
        var barcodeTextField = this.tfBarcode;
        barcodeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                barcodeTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    public void setPersonInstance(Person instance)
    {
        this.person = instance;

        var subscribedModel = SilverPublisher.getInstance().getUniqueSubscriberByModelId(this.person, Person.class);

        if (subscribedModel != null) {
            this.labelUncommittedChanges.setVisible(true);

            this.person = subscribedModel;
        }

        this.tfBarcode.setText(this.person.getIdentifier());
        this.tfFirstName.setText(this.person.first_name);
        this.tfMiddleName.setText(this.person.middle_names);
        this.tfLastName.setText(this.person.last_name);
        this.tfAddressLine1.setText(this.person.address_line_1);
        this.tfAddressLine2.setText(this.person.address_line_2);
        this.tfCity.setText(this.person.city);
        this.tfState.setText(this.person.state);
        this.tfZip.setText(this.person.zip_code);
        this.cbIsStaff.setSelected(this.person.user.is_staff);
        this.cbHeadOfFamily.setSelected(this.person.is_head_of_family);
    }

    public Person getPersonInstance()
    {
        return this.person;
    }

    private void closeScene()
    {
        this.context.getCurrentState().onRevertState();

        this.context.getCurrentState().onRefreshData();

        ((Stage)((Node)this.bClose).getScene().getWindow()).close();
    }

    private void toggleEditMode(boolean isEditing)
    {
        this.editedPerson = isEditing ? this.person.clone() : null;

        this.gEditing.setVisible(isEditing);
        this.gViewing.setVisible(!isEditing);

        this.tfBarcode.setEditable(isEditing);
        this.tfFirstName.setEditable(isEditing);
        this.tfMiddleName.setEditable(isEditing);
        this.tfLastName.setEditable(isEditing);
        this.tfAddressLine1.setEditable(isEditing);
        this.tfAddressLine2.setEditable(isEditing);
        this.tfCity.setEditable(isEditing);
        this.tfState.setEditable(isEditing);
        this.tfZip.setEditable(isEditing);
        this.cbIsStaff.setDisable(!isEditing);
        this.cbHeadOfFamily.setDisable(!isEditing);
    }

    private void mapPropertiesToEditModel()
    {
        this.editedPerson.barcode_identifier = Integer.parseUnsignedInt(this.tfBarcode.getText());
        this.editedPerson.first_name = this.tfFirstName.getText();
        this.editedPerson.middle_names = this.tfMiddleName.getText();
        this.editedPerson.last_name = this.tfLastName.getText();
        this.editedPerson.address_line_1 = this.tfAddressLine1.getText();
        this.editedPerson.address_line_2 = this.tfAddressLine2.getText();
        this.editedPerson.city = this.tfCity.getText();
        this.editedPerson.state = this.tfState.getText();
        this.editedPerson.zip_code = this.tfZip.getText();
        this.editedPerson.user.is_staff = this.cbIsStaff.isSelected();
        this.editedPerson.is_head_of_family = this.cbHeadOfFamily.isSelected();
    }

    public void doClose()
    {
        this.closeScene();
    }

    public void doEdit()
    {
        SilverPublisher.getInstance().unsubscribeUniqueModelById(this.person, Person.class);

        this.toggleEditMode(true);
    }

    public void doEditCancel()
    {
        var resp = SilverUtilities.ShowAlertYesNoConfirmation(
                "Are you sure? You will lose any unsaved changes.",
                "Cancel Edit");

        if (resp != ButtonType.YES) return;

        this.toggleEditMode(false);
    }

    public void doSave()
    {
        this.mapPropertiesToEditModel();

        this.editedPerson.commit();

        SilverUtilities.ShowAlert(
                this.editedPerson.getDisplayName() + " was saved successfully!",
                "Success!",
                Alert.AlertType.INFORMATION);

        this.closeScene();
    }

    public void doSubscribe()
    {
        this.mapPropertiesToEditModel();

        // Clone the edited person object so the reference to 'editedPerson' does not need to remain.
        if (!SilverPublisher.getInstance().isUniqueModelAlreadySubscribed(this.editedPerson, Person.class)) {
            SilverPublisher.getInstance().subscribe(this.editedPerson.clone());

            this.context.getController().flagPendingChanges();
        }

        this.closeScene();
    }
}
