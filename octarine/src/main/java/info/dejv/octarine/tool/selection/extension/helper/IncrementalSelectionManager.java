package info.dejv.octarine.tool.selection.extension.helper;

import static java.util.Objects.requireNonNull;

import java.awt.*;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.tool.selection.extension.feedback.IncrementalSelectionDynamicFeedback;
import info.dejv.octarine.tool.selection.extension.feedback.IncrementalSelectionDynamicFeedback.Type;

/**
 * "Increamental selection" manager.
 * Handles proper functionality of "Add to selection" / "Remove from selection" scenarios.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class IncrementalSelectionManager {

    @Autowired
    private Octarine editor;

    private IncrementalSelectionListener listener;

    @Autowired
    private IncrementalSelectionDynamicFeedback incrementalSelectionDynamicFeedback;


    public void activate(MouseEvent e, IncrementalSelectionListener listener) {
        requireNonNull(listener, "listener is null");

        this.listener = listener;

        Scene scene = editor.getViewer().getScene();
        if (scene != null) {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
            scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
        }
        updateSelectionFeedback(e.isShortcutDown(), e.isAltDown(), e.getScreenX(), e.getScreenY());
    }


    public void refresh(MouseEvent e) {
        updateSelectionFeedback(e.isShortcutDown(), e.isAltDown(), e.getScreenX(), e.getScreenY());
    }


    @SuppressWarnings("UnusedParameters")
    public void commit(MouseEvent e) {
        Type currentType = incrementalSelectionDynamicFeedback.getType();

        if (currentType == null) {
            listener.replaceSelection();
        } else {

            switch (currentType) {
                case ADD:
                    listener.addToSelection();
                    break;

                case REMOVE:
                    listener.removeFromSelection();
                    break;
            }
        }
    }


    public void deactivate() {
        incrementalSelectionDynamicFeedback.remove();
        listener = null;

        Scene scene = editor.getViewer().getScene();
        if (scene != null) {
            scene.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
            scene.removeEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
        }
    }


    private void handleKeyPressed(KeyEvent e) {
        Point p = MouseInfo.getPointerInfo().getLocation();
        updateSelectionFeedback(e.isControlDown(), e.isAltDown(), p.x, p.y);
    }


    private void handleKeyReleased(KeyEvent e) {
        Point p = MouseInfo.getPointerInfo().getLocation();
        updateSelectionFeedback(e.isControlDown(), e.isAltDown(), p.x, p.y);
    }



    private void updateSelectionFeedback(boolean ctrl, boolean alt, double x, double y) {
        Type currentType = incrementalSelectionDynamicFeedback.getType();

        if ((ctrl) && (!Type.ADD.equals(currentType))) {
            incrementalSelectionDynamicFeedback.set(Type.ADD);
            return;
        }

        if ((alt) && (!Type.REMOVE.equals(currentType))) {
            incrementalSelectionDynamicFeedback.set(Type.REMOVE);
            return;
        }

        if ((!alt) && (!ctrl) && (currentType != null)) {
            incrementalSelectionDynamicFeedback.remove();
            return;
        }

        if (currentType != null) {
            incrementalSelectionDynamicFeedback.setMouseLocation(x, y);
        }
    }
}
