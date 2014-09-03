package app.dejv.impl.octarine.tool.selection.extension.incremental;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import javax.annotation.PreDestroy;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import app.dejv.impl.octarine.tool.selection.SelectionActionListener;
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
    private SelectionActionListener listener;
    private Optional<IncrementType> type;
    private boolean isActive = false;

    private boolean addKeyDown = false;
    private boolean subtractKeyDown = false;

    private boolean canAdd = true;
    private boolean canSubtract = true;


    public IncrementalSelectionManager(Octarine octarine, IncrementalSelectionDynamicFeedback incrementalSelectionFeedback) {
        requireNonNull(octarine, "octarine is null");
        requireNonNull(incrementalSelectionFeedback, "incrementalSelectionFeedback is null");

        this.scene = octarine.getView().getScene();
        requireNonNull(scene, "scene is null");

        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyEvent);

        this.incrementalSelectionFeedback = incrementalSelectionFeedback;

        updateIncrementType();
    }


    @PreDestroy
    public void dispose() {
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyEvent);
    }


    public void setFeedbackLocation(double x, double y) {
        incrementalSelectionFeedback.setLocation(x, y);
    }


    public void activate(SelectionActionListener listener, boolean canAdd, boolean canSubtract) {
        requireNonNull(listener, "listener is null");
        this.canAdd = canAdd;
        this.canSubtract = canSubtract;
        this.listener = listener;

        isActive = true;

        //Handle the special case, when force-hiding the symbol due to different blocker setting to avoid momentary "jump" of fading-out symbol
        if ((addKeyDown && !canAdd) || (subtractKeyDown && !canSubtract)) {
            incrementalSelectionFeedback.blockFadeOut();
        }
        updateIncrementType();
    }


    public void deactivate() {
        if (!isActive)
            return;

        incrementalSelectionFeedback.deactivate();

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


    public boolean isActive() {
        return isActive;
    }


    private void handleKeyEvent(KeyEvent e) {
        this.addKeyDown = e.isShiftDown();
        this.subtractKeyDown = e.isAltDown();

        updateIncrementType();
    }


    private void updateIncrementType() {

        type = (canSubtract && subtractKeyDown) ? Optional.of(IncrementType.SUBTRACT)
                : (canAdd && addKeyDown) ? Optional.of(IncrementType.ADD)
                : Optional.empty();

        updateFeedback();
    }


    private void updateFeedback() {
        if (type.isPresent()) {
            incrementalSelectionFeedback.setType(type.get());
        }
        if (isActive) {
            if (type.isPresent()) {
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

}
