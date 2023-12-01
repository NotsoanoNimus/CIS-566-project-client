package xyz.xmit.silverclient.utilities;

import javafx.stage.Stage;
import xyz.xmit.silverclient.models.InventoryItemInstance;
import xyz.xmit.silverclient.ui.controllers.HookedController;
import xyz.xmit.silverclient.ui.controllers.ItemInstanceDetailController;
import xyz.xmit.silverclient.ui.controllers.PrimaryWindowController;
import xyz.xmit.silverclient.ui.statemachine.SilverApplicationContext;

/**
 * DESIGN PATTERN: Builder, but also a kind of Factory.
 * <br /><br />
 * Here we compose FxmlSceneBuilder types to construct and show different form components and
 * GUI windows. This is called a 'Director' in the class name to distinguish it as using the
 * Builder to accomplish its work and to >>DIRECT<< the construction of builder objects.
 * <br /><br />
 * However, in its design it is also a Factory because it uses singleton facade methods to
 * construct complex objects with the builder. So the exact pattern here is a little ambiguous
 * or is just a combination of a few different ones.
 */
public final class SceneDirector
{
    public static void constructLoginWindow(Stage stageReference)
    {
        new FxmlSceneBuilder("authentication-window.fxml", stageReference)
                .setWidth(600)
                .setHeight(400)
                .setTitle("Silver Library Management | Log In")
                .setUndecorated(false)
                .setResizeable(false)
                .setExitMonitoring(false)
                .build();
    }

    public static void constructPrimaryWindow(Stage stageReference)
    {
        new FxmlSceneBuilder("primary-window.fxml", stageReference)
                .setWidth(1000)
                .setHeight(750)
                .setTitle("Silver Library Management | Dashboard")
                .setResizeable(false)
                .setUndecorated(false)
                .buildWithBaseControllerAction(PrimaryWindowController.class);
    }

    public static HookedController constructItemInstancePopupWindow(
            InventoryItemInstance instance,
            SilverApplicationContext context)
    {
        return new FxmlSceneBuilder("item-instance-detail.fxml", new Stage())
                .setWidth(800)
                .setHeight(600)
                .setTitle("Item Instance | #" + instance.barcode_sku + " | " + instance.getTitle())
                .setResizeable(false)
                .setUndecorated(true)
                .buildWithBaseControllerAction(ItemInstanceDetailController.class, context);
    }
}
