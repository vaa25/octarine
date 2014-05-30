package info.dejv.octarine.tool.selection.editmode;

import java.util.List;

import info.dejv.octarine.controller.Controller;

/**
 * Edit modes API<br/>
 * Edit modes are handlers for specialized editation actions on current selection (like delete, translate, scale, etc.)
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface EditMode {

    void init();

    void activate();

    void deactivate();

    void selectionUpdated(List<Controller> newSelection);

    boolean isEnabled();

    boolean isActive();
}