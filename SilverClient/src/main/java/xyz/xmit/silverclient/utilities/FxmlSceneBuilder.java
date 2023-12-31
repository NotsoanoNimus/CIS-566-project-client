package xyz.xmit.silverclient.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import xyz.xmit.silverclient.SilverLibraryApplication;
import xyz.xmit.silverclient.ui.controllers.HookedController;
import xyz.xmit.silverclient.ui.statemachine.SilverApplicationContext;

import java.io.IOException;

/**
 * See parent interface for pattern information.
 */
public final class FxmlSceneBuilder
    implements IFxmlSceneBuilder
{
    private String fxmlResourceName;

    private String title;

    private int width;

    private int height;

    private boolean isResizeable;

    // The stylesheets always implicitly include a reference to the GLOBAL sheet by default.
    private String[] stylesheets = new String[] { "global.css" };

    private Stage stage = new Stage();

    private Scene scene;

    private Object sceneUserData = null;

    private boolean closesStageOnBuildError = true;

    private boolean undecorated = true;

    private boolean exitMonitoring = true;

    /**
     * @see FxmlSceneBuilder
     */
    public FxmlSceneBuilder(String fxmlResourceName, Stage targetStage) {
        this.fxmlResourceName = fxmlResourceName;
        this.stage = targetStage;
    }

    public <T extends Class<? extends HookedController>> HookedController buildWithBaseControllerAction(T controllerType)
    {
        return this.buildWithBaseControllerAction(controllerType, null);
    }

    public <T extends Class<? extends HookedController>> HookedController buildWithBaseControllerAction(
            T controllerType,
            SilverApplicationContext injectedContext)
    {
        this.build();

        var loader = (Pair<Object, FXMLLoader>)this.stage.getScene().getUserData();

        HookedController controller = loader.getValue().getController();
        controller.controllerEntryHook();

        if (injectedContext != null) {
            controller.setApplicationContext(injectedContext);
        }

        this.stage.getScene().setUserData(null);

        return controller;
    }

    public boolean build() {
        // Create a new loader with the primary window's FXML.
        var loader = new FXMLLoader(SilverLibraryApplication.class.getResource(this.fxmlResourceName));

        try {
            // Attempt to create the new scene with the resource FXML and load it onto the target stage.
            var primaryWindowScene = new Scene(loader.load());

            // Preserve a loader reference to get in case it's needed in an out Build wrapper call.
            primaryWindowScene.setUserData(new Pair<Object, FXMLLoader>(this.sceneUserData, loader));

            // Load all relevant stylesheets.
            for (var resourceName : this.stylesheets) {
                primaryWindowScene.getStylesheets()
                        .add(SilverLibraryApplication.class.getResource(resourceName).toExternalForm());
            }

            // Decorate or un-decorate based on settings. Cannot be done if the stage has already been shown.
            if (!this.stage.isShowing()) {
                if (this.undecorated && this.stage.getStyle() != StageStyle.UNDECORATED) {
                    this.stage.initStyle(StageStyle.UNDECORATED);
                } else if (!this.undecorated && this.stage.getStyle() != StageStyle.DECORATED) {
                    this.stage.initStyle(StageStyle.DECORATED);
                }
            }

            // Add default Silver icon to the stage.
            if (this.stage.getIcons().isEmpty()) {
                var image = SilverLibraryApplication.class.getResourceAsStream("images/silver_logo.png");
                if (image != null) {
                    this.stage.getIcons().add(new Image(image));
                }
            }

            // Set the scene and show.
            this.stage.setScene(primaryWindowScene);
            this.stage.show();

            // Add an exit monitoring interface.
            if (this.exitMonitoring) {
                primaryWindowScene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
            }

            return true;
        } catch (IOException ioException) {
            ioException.printStackTrace();

            // Show an error alert about failing to load the scene onto the stage.
            SilverUtilities.ShowAlert("Failed to load the next panel onto the stage.", "Failed to load scene!");

            // Close and exit the given stage reference if set to.
            if (this.closesStageOnBuildError) {
                this.stage.close();
            }

            return false;
        }
    }

    private void closeWindowEvent(WindowEvent event)
    {
        if (!SilverUtilities.ShowLogoutDialog()) {
            event.consume();
        }
    }

    public String getFxmlResourceName() {
        return fxmlResourceName;
    }

    public String getTitle() {
        return title;
    }

    public IFxmlSceneBuilder setTitle(String title) {
        this.title = title;
        this.stage.setTitle(title);

        return this;
    }

    public int getWidth() {
        return width;
    }

    public IFxmlSceneBuilder setWidth(int width) {
        this.width = width;
        this.stage.setWidth(width);

        return this;
    }

    public int getHeight() {
        return height;
    }

    public IFxmlSceneBuilder setHeight(int height) {
        this.height = height;
        this.stage.setHeight(height);

        return this;
    }

    public boolean isUndecorated() {
        return undecorated;
    }

    public IFxmlSceneBuilder setUndecorated(boolean isUndecorated) {
        this.undecorated = isUndecorated;

        return this;
    }

    public boolean isExitMonitoring() {
        return exitMonitoring;
    }

    public IFxmlSceneBuilder setExitMonitoring(boolean isExitMonitoring) {
        this.exitMonitoring = isExitMonitoring;

        return this;
    }

    public boolean isResizeable() {
        return isResizeable;
    }

    public IFxmlSceneBuilder setResizeable(boolean resizeable) {
        this.isResizeable = resizeable;
        this.stage.setResizable(resizeable);

        return this;
    }

    public String[] getStylesheets() {
        return stylesheets;
    }

    public IFxmlSceneBuilder setStylesheets(String[] stylesheets) {
        this.stylesheets = stylesheets;

        return this;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isClosesStageOnBuildError() {
        return closesStageOnBuildError;
    }

    public IFxmlSceneBuilder setClosesStageOnBuildError(boolean closesStageOnBuildError) {
        this.closesStageOnBuildError = closesStageOnBuildError;

        return this;
    }

    public IFxmlSceneBuilder setSceneUserData(Object data) {
        this.sceneUserData = data;

        return this;
    }

    public Object getSceneUserData() {
        return this.sceneUserData;
    }
}
