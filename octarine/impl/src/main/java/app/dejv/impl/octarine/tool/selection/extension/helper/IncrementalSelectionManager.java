package app.dejv.impl.octarine.tool.selection.extension.helper;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import app.dejv.impl.octarine.tool.selection.extension.feedback.IncrementalSelectionDynamicFeedback;
import app.dejv.octarine.Octarine;

/**
 * "Increamental selection" manager.
 * Handles proper functionality of "Add to selection" / "Remove from selection" scenarios.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class IncrementalSelectionManager {

    private final Scene scene;
    private final IncrementalSelectionDynamicFeedback incrementalSelectionFeedback;
    private IncrementalSelectionListener listener;
    private Optional<IncrementType> type;
    private boolean isActive = false;


    public IncrementalSelectionManager(Octarine octarine, IncrementalSelectionDynamicFeedback incrementalSelectionFeedback) {
        requireNonNull(octarine, "octarine is null");
        requireNonNull(incrementalSelectionFeedback, "incrementalSelectionFeedback is null");

        this.scene = octarine.getViewer().getScene();
        requireNonNull(scene, "scene is null");

        this.incrementalSelectionFeedback = incrementalSelectionFeedback;
    }


    public void setFeedbackLocation(double x, double y) {
        incrementalSelectionFeedback.setLocation(x, y);
    }


    public void activate(IncrementalSelectionListener listener) {
        requireNonNull(listener, "listener is null");

        this.listener = listener;

        if (isActive) {
            return;
        }

        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyEvent);

        isActive = true;
    }


    public void deactivate() {
        if (!isActive)
            return;

        incrementalSelectionFeedback.deactivate();

        scene.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyEvent);

        isActive = false;
    }


    public void commit() {
        if (type.isPresent()) {
            switch (type.get()) {
                case ADD:
                    listener.addToSelection();
                    break;

                case SUBTRACT:
                    listener.removeFromSelection();
                    break;
            }
        } else {
            listener.replaceSelection();
        }
    }


    private void handleKeyEvent(KeyEvent e) {
        updateKeyStates(e.isControlDown(), e.isAltDown());
    }


    private void updateKeyStates(boolean ctrl, boolean alt) {
        type = (alt) ? Optional.of(IncrementType.SUBTRACT) : Optional.empty();
        type = (ctrl) ? Optional.of(IncrementType.ADD) : Optional.empty();

        if (type.isPresent()) {
            incrementalSelectionFeedback.setType(type.get());

            if (!incrementalSelectionFeedback.isActive()) {
                incrementalSelectionFeedback.activate();
            }

        } else {
            if (incrementalSelectionFeedback.isActive()) {
                incrementalSelectionFeedback.deactivate();
            }
        }
    }

}
