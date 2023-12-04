package xyz.xmit.silverclient.utilities;

import javafx.stage.Stage;
import xyz.xmit.silverclient.ui.controllers.HookedController;
import xyz.xmit.silverclient.ui.statemachine.SilverApplicationContext;

/**
 * DESIGN PATTERN: Builder (Creational)
 * <br /><br />
 * This class makes use of the Builder pattern to manufacture clean UIs (scenes with stages
 * and stage settings), without having to ceremoniously dance around using some explosion of
 * constructor overloads like:
 * <pre>
 *     public FxmlSceneBuilder(String resource, String[] stylesheets, boolean isLocked) { ... }
 *     public FxmlSceneBuilder(String resource, String[] stylesheets) { ... }
 * </pre>
 * Instead, the method-chaining on the setter methods makes for clean usages of this class
 * in varying situations, as well as fluent (human-readable) constructions of new UI components.
 * <br /><br />
 * There is also a related Factory/Director for this interface in the "SceneDirector" class.
 */
public interface IFxmlSceneBuilder
{
    public <T extends Class<? extends HookedController>> HookedController buildWithBaseControllerAction(T controllerType);

    public <T extends Class<? extends HookedController>> HookedController buildWithBaseControllerAction(
            T controllerType,
            SilverApplicationContext injectedContext);

    public boolean build();

    public String getFxmlResourceName();

    public String getTitle();

    public IFxmlSceneBuilder setTitle(String title);

    public int getWidth();

    public IFxmlSceneBuilder setWidth(int width);

    public int getHeight();

    public IFxmlSceneBuilder setHeight(int height);

    public boolean isUndecorated();

    public IFxmlSceneBuilder setUndecorated(boolean isUndecorated);

    public boolean isExitMonitoring();

    public IFxmlSceneBuilder setExitMonitoring(boolean isExitMonitoring);

    public boolean isResizeable();

    public IFxmlSceneBuilder setResizeable(boolean resizeable);

    public String[] getStylesheets();

    public IFxmlSceneBuilder setStylesheets(String[] stylesheets);

    public Stage getStage();

    public boolean isClosesStageOnBuildError();

    public IFxmlSceneBuilder setClosesStageOnBuildError(boolean closesStageOnBuildError);

    public IFxmlSceneBuilder setSceneUserData(Object data);

    public Object getSceneUserData();
}
