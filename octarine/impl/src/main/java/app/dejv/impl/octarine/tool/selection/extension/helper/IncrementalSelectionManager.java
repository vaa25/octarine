package app.dejv.impl.octarine.tool.selection.extension.helper;

import static java.util.Objects.requireNonNull;

import java.awt.*;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import app.dejv.impl.octarine.tool.selection.extension.feedback.IncrementalSelectionDynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.feedback.IncrementalSelectionDynamicFeedback.Type;
import app.dejv.octarine.Octarine;

/**
 * "Increamental selection" manager.
 * Handles proper functionality of "Add to selection" / "Remove from selection" scenarios.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class IncrementalSelectionManager {

    public enum IncrementType {
        ADD,
        REMOVE
    }


    private final Octarine octarine;
    private final IncrementalSelectionDynamicFeedback incrementalSelectionFeedback;
    private IncrementalSelectionListener listener;
    private Optional<IncrementType> type;

    public IncrementalSelectionManager(Octarine octarine, IncrementalSelectionDynamicFeedback incrementalSelectionFeedback) {
        requireNonNull(octarine, "octarine is null");
        requireNonNull(incrementalSelectionFeedback, "incrementalSelectionFeedback is null");

        this.octarine = octarine;
        this.incrementalSelectionFeedback = incrementalSelectionFeedback;
    }


    public void activate(IncrementalSelectionListener listener) {
        requireNonNull(listener, "listener is null");

        this.listener = listener;

        final Scene scene = octarine.getViewer().getScene();
        if (scene != null) {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
            scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
            scene.addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        }
    }


    public void deactivate() {
        incrementalSelectionFeedback.deactivate();

        final Scene scene = octarine.getViewer().getScene();
        if (scene != null) {
            scene.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
            scene.removeEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
            scene.removeEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        }
    }


    public void commit() {
        if (type.isPresent()) {
            switch (type.get()) {
                case ADD:
                    listener.addToSelection();
                    break;

                case REMOVE:
                    listener.removeFromSelection();
                    break;
            }
        } else {
            listener.replaceSelection();
        }
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        incrementalSelectionFeedback.setMouseLocation(mouseEvent.getX(), mouseEvent.getY());
    }


    private void handleKeyPressed(KeyEvent e) {
        updateKeyStates(e.isControlDown(), e.isAltDown());
    }


    private void handleKeyReleased(KeyEvent e) {
        updateKeyStates(e.isControlDown(), e.isAltDown());
    }



    public void updateKeyStates(boolean ctrl, boolean alt) {
        type = (alt) ? Optional.of(IncrementType.REMOVE) : Optional.empty();
        type = (ctrl) ? Optional.of(IncrementType.ADD) : Optional.empty();

        if (type.isPresent()) {
            incrementalSelectionFeedback.setType(type.get());

            if (!incrementalSelectionFeedback.isActive()) {
                incrementalSelectionFeedback.activate();
            }

        }

        else {
            if (incrementalSelectionFeedback.isActive()) {
                incrementalSelectionFeedback.deactivate();
            }
        }
    }

}
