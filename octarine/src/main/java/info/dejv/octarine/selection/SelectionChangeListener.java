package info.dejv.octarine.selection;

import info.dejv.octarine.controller.Controller;
import java.util.List;

/**
 *
 * @author dejv
 */
public interface SelectionChangeListener {

    /**
     Notification about update in selection
     @param sender Notification sender
     @param selection Current selection
     @param added Elements selected since last notification
     @param removed Elements deselected since last notification
     */
    @SuppressWarnings("UnusedParameters")
    void selectionChanged(SelectionManager sender, List<Controller> selection, List<Controller> added, List<Controller> removed);
}
