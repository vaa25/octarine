package info.dejv.octarine.tool.selection.editmode;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.tool.selection.request.DeleteRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * "Delete" edit mode<br/>
 * Deletes the selection on DEL key
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeDelete
        extends AbstractEditMode {


    public EditModeDelete(Octarine octarine) {
        super(DeleteRequest.class, octarine);
    }


    @Override
    public void activate() {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
    }

    @Override
    public void deactivate() {
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
                .map((controller) -> controller.getParent())
                .distinct()
                .collect(Collectors.toSet());

        executeOnSet(new DeleteRequest(new HashSet<>(selection)), selectionParents);
    }
}
