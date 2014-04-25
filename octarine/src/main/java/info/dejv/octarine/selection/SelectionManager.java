package info.dejv.octarine.selection;

import info.dejv.octarine.controller.Controller;
import java.util.List;

/**
 *
 * @author dejv
 */
public interface SelectionManager {

    List<Controller> getSelection();

    boolean contains(Controller controller);

    void replace(List<Controller> selectables);

    void add(List<Controller> selectables);

    void remove(List<Controller> selectables);

    void deselectAll();

    void addSelectionChangeListener(SelectionChangeListener listener);

    void removeSelectionChangeListener(SelectionChangeListener listener);
}
