package xyz.xmit.silverclient.ui.controllers;

public abstract class HookedController
{
    /**
     * A function called optionally when the scene related to the FXML controller is constructed.
     *
     * @see xyz.xmit.silverclient.utilities.FxmlSceneBuilder#buildWithBaseControllerAction(Class)
     */
    public abstract void controllerEntryHook();
}
