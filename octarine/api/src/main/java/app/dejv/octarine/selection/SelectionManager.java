package app.dejv.octarine.selection;

import java.util.List;

import app.dejv.octarine.controller.Controller;

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
