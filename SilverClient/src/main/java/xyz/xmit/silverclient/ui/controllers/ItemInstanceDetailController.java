package xyz.xmit.silverclient.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import xyz.xmit.silverclient.models.InventoryItemInstance;

public final class ItemInstanceDetailController
    extends HookedController
{
    private InventoryItemInstance inventoryItemInstance;

    @FXML
    public Button bClose;

    @FXML
    public Button bEditItem;

    @Override
    public void controllerEntryHook()
    {
    }

    public void setInventoryItemInstance(InventoryItemInstance instance)
    {
        this.inventoryItemInstance = instance;
    }

    public void doClose(MouseEvent event)
    {
        this.context.getCurrentState().onRevertState();

        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
