package info.dejv.octarine.actionhandler.selection.helpers;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.actionhandler.selection.feedback.IncrementalSelectionFeedback;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author dejv
 */
public class IncrementalSelectionHelper {

    private final Octarine editor;
    private final IncrementalSelectionListener listener;

    public IncrementalSelectionHelper(Octarine editor, IncrementalSelectionListener listener) {
        Objects.requireNonNull(editor, "editor is NULL");
        Objects.requireNonNull(listener, "listener is NULL");

        this.editor = editor;
        this.listener = listener;
    }


    public void activate(MouseEvent e) {
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


    public void commit(MouseEvent e) {
        if (e.isShortcutDown()) {
            removeSelectionFeedback();
            listener.addToSelection();
            return;
        }

        if (e.isAltDown()) {
            removeSelectionFeedback();
            listener.removeFromSelection();
            return;
        }

        listener.replaceSelection();
    }


    public void deactivate() {
        removeSelectionFeedback();
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
        checkAndUpdateFeedback(ctrl, IncrementalSelectionFeedback.Type.ADD);
        checkAndUpdateFeedback(alt, IncrementalSelectionFeedback.Type.REMOVE);

        IncrementalSelectionFeedback isf = IncrementalSelectionFeedback.getInstance();

        if ((!ctrl) && (!alt) && (isf != null)) {
            removeSelectionFeedback();
        }

        if (isf != null) {
            isf.setMouseLocation(x, y);
        }
    }


    private void addSelectionFeedback(IncrementalSelectionFeedback.Type fbType) {
        IncrementalSelectionFeedback.add(fbType, editor);
    }

    private void removeSelectionFeedback() {
        IncrementalSelectionFeedback.remove();
    }

    private void checkAndUpdateFeedback(boolean modifier, IncrementalSelectionFeedback.Type type) {
        if (modifier) {
            IncrementalSelectionFeedback isf = IncrementalSelectionFeedback.getInstance();

            if ((isf != null) && (isf.getType() != type)) {
                removeSelectionFeedback();
            }

            if (isf == null) {
                addSelectionFeedback(type);
            }
        }
    }

}
