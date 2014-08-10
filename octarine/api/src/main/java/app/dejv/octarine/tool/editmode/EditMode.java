package app.dejv.octarine.tool.editmode;

import java.util.List;

import app.dejv.octarine.controller.Controller;


/**
 * Edit modes API<br/>
 * Edit modes are handlers for specialized editation actions on current selection (like delete, translate, scale, etc.)
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface EditMode {

    /**
     * Initialize the Edit Mode.
     * Call this method when "Scene" is available, not sooner!
     */
    void initWithSceneAvailable();

    void activate();

    void deactivate();

    void selectionUpdated(List<Controller> newSelection);

    boolean isEnabled();

    boolean isActive();
}
