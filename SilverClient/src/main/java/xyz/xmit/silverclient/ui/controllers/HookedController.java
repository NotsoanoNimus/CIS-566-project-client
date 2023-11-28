package xyz.xmit.silverclient.ui.controllers;

import xyz.xmit.silverclient.ui.statemachine.SilverApplicationContext;

/**
 * Base class which fires a hook function when the FXMLLoader class loads the related FXML form.
 * It also allows the application context to be set directly, so parent forms can provide it.
 *
 * @see xyz.xmit.silverclient.utilities.FxmlSceneBuilder#buildWithBaseControllerAction(Class)
 */
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
