package app.dejv.impl.octarine.tool.selection.extension.helper;

import static java.util.Objects.requireNonNull;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import app.dejv.impl.octarine.tool.selection.extension.feedback.MarqueeSelectionDynamicFeedback;
import app.dejv.impl.octarine.utils.MathUtils;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class MarqueeSelectionManager implements SelectionActionListener {

    private final MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback;

    private final IncrementalSelectionManager incrementalSelectionManager;

    private boolean isActive = false;

    private boolean drag = false;
    private double initialX;
    private double initialY;

    private Node view;
    private MarqueeSelectionActionListener listener;


    public MarqueeSelectionManager(MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback, IncrementalSelectionManager incrementalSelectionManager) {
        this.marqueeSelectionDynamicFeedback = marqueeSelectionDynamicFeedback;
        this.incrementalSelectionManager = incrementalSelectionManager;

        requireNonNull(marqueeSelectionDynamicFeedback);
        requireNonNull(incrementalSelectionManager);
    }


    public void activate(Node view, MarqueeSelectionActionListener listener) {
        if (isActive) {
            return;
        }

        requireNonNull(view, "view is null");
        requireNonNull(listener, "listener is null");

        this.view = view;
        this.listener = listener;

        view.addEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }


    public void deactivate() {
        if (!isActive) {
            return;
        }
        view.removeEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        view.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        view.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }


    @Override
    public void addToSelection() {
        listener.addToSelection(marqueeSelectionDynamicFeedback.getBoundsInLocal());
    }


    @Override
    public void removeFromSelection() {
        listener.removeFromSelection(marqueeSelectionDynamicFeedback.getBoundsInLocal());
    }


    @Override
    public void replaceSelection() {
        listener.replaceSelection(marqueeSelectionDynamicFeedback.getBoundsInLocal());
    }


    @Override
    public void deselectAll() {
        listener.deselectAll();
    }


    private void handleDragDetected(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            drag = true;

            initialX = e.getX();
            initialY = e.getY();

            marqueeSelectionDynamicFeedback.setInitialCoords(initialX, initialY);

            marqueeSelectionDynamicFeedback.activate();
            incrementalSelectionManager.activate(this, true, true);
            e.consume();
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            marqueeSelectionDynamicFeedback.setCurrentCoords(e.getX(), e.getY());

            incrementalSelectionManager.setFeedbackLocation(MathUtils.mean(initialX, e.getX()), MathUtils.mean(initialY, e.getY()));
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            incrementalSelectionManager.commit();
            incrementalSelectionManager.deactivate();
            marqueeSelectionDynamicFeedback.deactivate();
            drag = false;
        } else {
            deselectAll();
        }
    }

}
