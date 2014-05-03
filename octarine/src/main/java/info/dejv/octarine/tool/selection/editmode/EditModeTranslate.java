/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine.tool.selection.editmode;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.tool.selection.SelectionTool;
import info.dejv.octarine.tool.selection.request.TranslateRequest;
import info.dejv.octarine.utils.ControllerUtils;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 * "Translate" edit mode<br/>
 * Translates the selection on mouse drag
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeTranslate
        extends AbstractEditMode {


    private boolean drag = false;
    private Point2D initialPosition;

    private final Map<Shape, Point2D> transformationFeedback = new HashMap<>();

    public EditModeTranslate(Octarine octarine) {
        super(TranslateRequest.class, octarine);
    }


    @Override
    protected void beforeSelectionUpdated() {
        deactivate();
    }

    @Override
    protected void afterSelectionUpdated() {
        activate();
    }


    @Override
    public void activate() {
        selection.stream().map((item) -> item.getView()).forEach((view) -> {
            LOG.trace("Activating on {}", view);
            view.addEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
            view.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
            view.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        });
    }

    @Override
    public void deactivate() {
        selection.stream().map((item) -> item.getView()).forEach((view) -> {
            LOG.trace("Deactivating on {}", view);
            view.removeEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
            view.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
            view.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        });
    }


    private void handleDragDetected(MouseEvent e) {
        if (drag) {
            return;
        }

        if (e.isPrimaryButtonDown()) {
            drag = true;
            initialPosition = new Point2D(e.getX(), e.getY());

            selection.stream().forEach((controller) -> {
                Shape shape = ControllerUtils.getShape(controller);
                shape.translateXProperty().unbind();
                shape.translateYProperty().unbind();
                shape.setOpacity(0.5);

                Bounds shapeBounds = shape.getBoundsInParent();
                transformationFeedback.put(shape, new Point2D(shapeBounds.getMinX() - e.getX(), shapeBounds.getMinY() - e.getY()));
            });

            addTransformationFeedback(e.getX(), e.getY());

            e.consume();
            SelectionTool.getInstance().getListeners().stream().forEach((listener) -> listener.onEditStarted());
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            moveTransformationFeedback(e.getX(), e.getY());
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            removeTransformationFeedback();
            executeOnSelection(new TranslateRequest(e.getX() - initialPosition.getX(), e.getY() - initialPosition.getY()));
            drag = false;
            SelectionTool.getInstance().getListeners().stream().forEach((listener) -> listener.onEditFinished());
        }
    }

    private void addTransformationFeedback(double x, double y) {
        moveTransformationFeedback(x, y);

        ObservableList<Node> activeFeedback = getOctarine().getActiveFeedback();
        transformationFeedback.keySet().stream().forEach((shape) -> activeFeedback.add(shape));
    }


    private void moveTransformationFeedback(double x, double y) {
        transformationFeedback.entrySet().stream().forEach((entry) -> {
            Shape shape = entry.getKey();
            Point2D initialOffset = entry.getValue();

            shape.setTranslateX(x + initialOffset.getX());
            shape.setTranslateY(y + initialOffset.getY());
        });
    }

    private void removeTransformationFeedback() {
        ObservableList<Node> activeFeedback = getOctarine().getActiveFeedback();
        transformationFeedback.keySet().stream().forEach((shape) -> activeFeedback.remove(shape));
        transformationFeedback.clear();
    }

}
