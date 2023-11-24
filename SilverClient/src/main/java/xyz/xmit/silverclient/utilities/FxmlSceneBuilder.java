package xyz.xmit.silverclient.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xyz.xmit.silverclient.SilverLibraryApplication;

import java.io.IOException;

/**
 * DESIGN PATTERN: Builder (Creational)
 * This class makes use of the Builder design pattern to manufacture clean UIs
 * (scenes with stages and stage settings), without having to ceremoniously dance
 * around using some crazy-complex constructor overloads like:
 *
 * <pre>
 *     public FxmlSceneBuilder(String resource, String[] stylesheets, boolean isLocked) { ... }
 *     public FxmlSceneBuilder(String resource, String[] stylesheets) { ... }
 * </pre>
 *
 * Instead, the method-chaining on the setter methods makes for clean usages of this class
 * in varying situations, as well as fluent (human-readable) constructions of new UI components.
 */
public final class FxmlSceneBuilder
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

    private boolean closesStageOnBuildError = true;

    private boolean undecorated = true;

    /**
     * @see FxmlSceneBuilder
     */
    public FxmlSceneBuilder(String fxmlResourceName, Stage targetStage) {
        this.fxmlResourceName = fxmlResourceName;
        this.stage = targetStage;
    }

    public boolean build() {
        // Create a new loader with the primary window's FXML.
        var loader = new FXMLLoader(SilverLibraryApplication.class.getResource(this.fxmlResourceName));

        try {
            // Attempt to create the new scene with the resource FXML and load it onto the target stage.
            var primaryWindowScene = new Scene(loader.load());

            // Load all relevant stylesheets.
            for (var resourceName : this.stylesheets) {
                primaryWindowScene.getStylesheets()
                        .add(SilverLibraryApplication.class.getResource(resourceName).toExternalForm());
            }

            if (!this.stage.isShowing()) {
                if (this.undecorated && this.stage.getStyle() != StageStyle.UNDECORATED) {
                    this.stage.initStyle(StageStyle.UNDECORATED);
                } else if (!this.undecorated && this.stage.getStyle() != StageStyle.DECORATED) {
                    this.stage.initStyle(StageStyle.DECORATED);
                }
            }

            this.stage.setScene(primaryWindowScene);
            this.stage.show();

            return true;
        } catch (IOException ioException) {
            // Show an error alert about failing to load the scene onto the stage.
            var alert = new Alert(
                    Alert.AlertType.ERROR,
                    "Failed to load the next panel onto the stage.",
                    ButtonType.OK);

            // More details, synchronously show the pop-up.
            alert.setHeaderText("Something went wrong!");
            alert.setTitle("Application Error");
            alert.showAndWait();

            // Close and exit the given stage reference if set to.
            if (this.closesStageOnBuildError) {
                this.stage.close();
            }

            return false;
        }
    }

    public String getFxmlResourceName() {
        return fxmlResourceName;
    }

    public String getTitle() {
        return title;
    }

    public FxmlSceneBuilder setTitle(String title) {
        this.title = title;
        this.stage.setTitle(title);

        return this;
    }

    public int getWidth() {
        return width;
    }

    public FxmlSceneBuilder setWidth(int width) {
        this.width = width;
        this.stage.setWidth(width);

        return this;
    }

    public int getHeight() {
        return height;
    }

    public FxmlSceneBuilder setHeight(int height) {
        this.height = height;
        this.stage.setHeight(height);

        return this;
    }

    public boolean isUndecorated() {
        return undecorated;
    }

    public FxmlSceneBuilder setUndecorated(boolean isUndecorated) {
        this.undecorated = isUndecorated;

        return this;
    }

    public boolean isResizeable() {
        return isResizeable;
    }

    public FxmlSceneBuilder setResizeable(boolean resizeable) {
        this.isResizeable = resizeable;
        this.stage.setResizable(resizeable);

        return this;
    }

    public String[] getStylesheets() {
        return stylesheets;
    }

    public FxmlSceneBuilder setStylesheets(String[] stylesheets) {
        this.stylesheets = stylesheets;

        return this;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isClosesStageOnBuildError() {
        return closesStageOnBuildError;
    }

    public FxmlSceneBuilder setClosesStageOnBuildError(boolean closesStageOnBuildError) {
        this.closesStageOnBuildError = closesStageOnBuildError;

        return this;
    }
}
