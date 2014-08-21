package app.dejv.impl.octarine.tool.selection.editmode.delete;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import app.dejv.impl.octarine.tool.selection.editmode.AbstractEditMode;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.Controller;

/**
 * "Delete" edit mode<br/>
 * Deletes the selection on DEL key
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeDelete
        extends AbstractEditMode {


    public EditModeDelete(Octarine octarine) {
        super(octarine, DeleteRequest.class);
    }


    @Override
    protected void doActivate() {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
    }

    @Override
    protected void doDeactivate() {
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
    }


    @Override
    protected boolean isSelectionItemSupported(Controller controller) {
        return controller.getParent().supports(requestType);
    }


    private void handleKeyReleased(KeyEvent e) {
        if (!KeyCode.DELETE.equals(e.getCode())) {
            return;
        }
        if (e.isAltDown() || e.isControlDown() || e.isMetaDown() || e.isShiftDown() || e.isShortcutDown()) {
            return;
        }

        e.consume();

        Set<Controller> selectionParents = selection.stream()
                .map(Controller::getParent)
                .distinct()
                .collect(Collectors.toSet());

        executeOnSet(new DeleteRequest(new HashSet<>(selection)), selectionParents);
    }
}
