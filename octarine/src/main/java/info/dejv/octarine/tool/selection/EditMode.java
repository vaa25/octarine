package info.dejv.octarine.tool.selection;

import info.dejv.octarine.controller.Controller;
import java.util.List;

/**
 * Edit modes API<br/>
 * Edit modes are handlers for specialized editation actions on current selection (like delete, translate, scale, etc.)
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface EditMode {

    void addListener(TransformListener listener);

    void activate();

    void deactivate();

    void selectionUpdated(List<Controller> newSelection);

    boolean isEnabled();
}
