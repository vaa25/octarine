package info.dejv.octarine.selection;

import java.util.ArrayList;
import java.util.List;

import info.dejv.octarine.controller.Controller;

/**
 *
 * @author dejv
 */
public class SelectionManagerImpl
        implements SelectionManager {

    private final List<SelectionChangeListener> listeners = new ArrayList<>();

    private final List<Controller> selection = new ArrayList<>();

    private final List<Controller> selected = new ArrayList<>();
    private final List<Controller> deselected = new ArrayList<>();

    private int updateCounter = 0;

    @Override
    public List<Controller> getSelection() {
        return new ArrayList<>(selection);
    }


    @Override
    public boolean contains(Controller controller) {
        return selection.contains(controller);
    }


    @Override
    public void deselectAll() {
        beginUpdate();
        try {
            remove(getSelection());
        } finally {
            endUpdate();
        }
    }


    @Override
    public void replace(List<Controller> selectables) {
        beginUpdate();
        try {
            deselectAll();
            add(selectables);
        } finally {
            endUpdate();
        }
    }


    @Override
    public void add(List<Controller> selectables) {
        beginUpdate();
        try {
            selectables.stream().forEach(s -> {
                selected.add(s);
                selection.add(s);
            });
        } finally {
            endUpdate();
        }
    }


    @Override
    public void remove(List<Controller> selectables) {
        beginUpdate();
        try {
            selectables.stream().forEach(s -> {
                deselected.add(s);
                selection.remove(s);
            });
        } finally {
            endUpdate();
        }
    }


    private void beginUpdate() {
        updateCounter++;
    }


    private void endUpdate() {
        updateCounter--;

        if (updateCounter == 0) {
            listeners.stream().forEach((listener) -> listener.selectionChanged(this, new ArrayList<>(selection), new ArrayList<>(selected), new ArrayList<>(deselected)));

            deselected.clear();
            selected.clear();
        }
    }

    @Override
    public void addSelectionChangeListener(SelectionChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeSelectionChangeListener(SelectionChangeListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

}
