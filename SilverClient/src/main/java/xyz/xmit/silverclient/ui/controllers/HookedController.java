package xyz.xmit.silverclient.ui.controllers;

import xyz.xmit.silverclient.ui.statemachine.SilverApplicationContext;

public abstract class HookedController
{
    protected SilverApplicationContext context;

    /**
     * A function called optionally when the scene related to the FXML controller is constructed.
     *
     * @see xyz.xmit.silverclient.utilities.FxmlSceneBuilder#buildWithBaseControllerAction(Class)
     */
    public abstract void controllerEntryHook();

    public void setApplicationContext(SilverApplicationContext context)
    {
        this.context = context;
    }
}
