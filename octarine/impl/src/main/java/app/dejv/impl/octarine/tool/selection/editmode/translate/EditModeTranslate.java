/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dejv.impl.octarine.tool.selection.editmode.translate;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;

import app.dejv.impl.octarine.tool.selection.editmode.AbstractEditMode;
import app.dejv.impl.octarine.utils.ControllerUtils;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.tool.EditationListener;

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
    private Translate fbTranslation = new Translate();

    private final Map<Shape, Point2D> transformationFeedback = new HashMap<>();


    public EditModeTranslate(Octarine octarine) {
        super(octarine, TranslateRequest.class);
    }


    @Override
    protected void doActivate() {
        selection.stream().map(Controller::getView).forEach((view) -> {
            LOG.trace("Activating on {}", view);
            view.addEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
            view.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
            view.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        });
    }

    @Override
    protected void doDeactivate() {
        selection.stream().map(Controller::getView).forEach((view) -> {
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
            initialPosition = new Point2D(e.getSceneX(), e.getSceneY());
            fbTranslation.setX(0);
            fbTranslation.setY(0);

            selection.stream().forEach((controller) -> {
                Shape shape = ControllerUtils.getShape(controller);
                shape.setOpacity(0.5);
                shape.getTransforms().add(1,fbTranslation);

                Bounds shapeBounds = shape.getBoundsInParent();
                transformationFeedback.put(shape, new Point2D(shapeBounds.getMinX() - e.getX(), shapeBounds.getMinY() - e.getY()));
            });

            addTransformationFeedback(e.getX(), e.getY());

            e.consume();

            getOctarine().getEditationListeners().forEach(EditationListener::onEditStarted);
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            moveTransformationFeedback(e.getSceneX(), e.getSceneY());
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            removeTransformationFeedback();

            final Dimension2D positionDelta = new Dimension2D(e.getSceneX() - initialPosition.getX(), e.getSceneY() - initialPosition.getY());

            executeOnSelection(new TranslateRequest(new Translate(positionDelta.getWidth(), positionDelta.getHeight())));

            drag = false;
            getOctarine().getEditationListeners().forEach(EditationListener::onEditFinished);
        }
    }

    private void addTransformationFeedback(double x, double y) {
        moveTransformationFeedback(x, y);

        final ObservableList<Node> activeFeedback = getOctarine().getLayerManager().getDynamicFeedbackLayer();
        transformationFeedback.keySet().stream().forEach(activeFeedback::add);
    }


    private void moveTransformationFeedback(double x, double y) {
        fbTranslation.setX(x - initialPosition.getX());
        fbTranslation.setY(y - initialPosition.getY());
    }

    private void removeTransformationFeedback() {
        final ObservableList<Node> activeFeedback = getOctarine().getLayerManager().getDynamicFeedbackLayer();
        transformationFeedback.keySet().stream().forEach(activeFeedback::remove);
        transformationFeedback.clear();
    }

}
