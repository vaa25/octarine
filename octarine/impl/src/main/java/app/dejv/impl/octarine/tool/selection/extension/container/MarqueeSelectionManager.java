package app.dejv.impl.octarine.tool.selection.extension.container;

import static java.util.Objects.requireNonNull;

import javafx.scene.Node;

import app.dejv.impl.octarine.drag.DefaultMouseDragHelper;
import app.dejv.octarine.input.MouseDragListener;
import app.dejv.impl.octarine.tool.selection.SelectionActionListener;
import app.dejv.impl.octarine.tool.selection.extension.incremental.IncrementalSelectionManager;
import app.dejv.impl.octarine.utils.MathUtils;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class MarqueeSelectionManager
        implements SelectionActionListener, MouseDragListener {

    private final MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback;
    private final IncrementalSelectionManager incrementalSelectionManager;
    private final DefaultMouseDragHelper mouseDragHelper;

    private boolean isActive = false;

    private double initialX;
    private double initialY;

    private MarqueeSelectionActionListener listener;


    public MarqueeSelectionManager(MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback, IncrementalSelectionManager incrementalSelectionManager, DefaultMouseDragHelper mouseDragHelper) {
        this.marqueeSelectionDynamicFeedback = marqueeSelectionDynamicFeedback;
        this.incrementalSelectionManager = incrementalSelectionManager;
        this.mouseDragHelper = mouseDragHelper;

        requireNonNull(marqueeSelectionDynamicFeedback, "marqueeSelectionDynamicFeedback is null");
        requireNonNull(incrementalSelectionManager, "incrementalSelectionManager is null");
        requireNonNull(mouseDragHelper, "mouseDragHelper is null");
    }


    public void activate(Node view, MarqueeSelectionActionListener listener) {
        if (isActive) {
            return;
        }

        requireNonNull(view, "view is null");
        requireNonNull(listener, "listener is null");

        this.listener = listener;

        mouseDragHelper.activate(view, this);
    }


    public void deactivate() {
        if (!isActive) {
            return;
        }
        mouseDragHelper.deactivate();
        deactivateContainerSelection();
    }


    @Override
    public void onDragStarted(double initialX, double initialY) {
        this.initialX = initialX;
        this.initialY = initialY;

        marqueeSelectionDynamicFeedback.setInitialCoords(initialX, initialY);

        marqueeSelectionDynamicFeedback.activate();
        incrementalSelectionManager.activate(this, true, true);
    }


    @Override
    public void onDragged(double currentX, double currentY) {
        marqueeSelectionDynamicFeedback.setCurrentCoords(currentX, currentY);
        incrementalSelectionManager.setFeedbackLocation(MathUtils.mean(initialX, currentX), MathUtils.mean(initialY, currentY));
    }


    @Override
    public void onDragCancelled(double finalX, double finalY) {
        deactivateContainerSelection();
    }


    @Override
    public void onDragCommited(double finalX, double finalY) {
        incrementalSelectionManager.commit();
        deactivateContainerSelection();
    }


    @Override
    public void onMouseReleased(double finalX, double finalY) {
        deselectAll();
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


    private void deactivateContainerSelection() {
        if (incrementalSelectionManager.isActive()) {
            incrementalSelectionManager.deactivate();
        }

        if (marqueeSelectionDynamicFeedback.isActive()) {
            marqueeSelectionDynamicFeedback.deactivate();
        }
    }
}
