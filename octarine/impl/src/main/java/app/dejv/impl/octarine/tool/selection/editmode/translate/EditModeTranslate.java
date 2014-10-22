/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dejv.impl.octarine.tool.selection.editmode.translate;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;

import app.dejv.impl.octarine.tool.selection.editmode.AbstractEditMode;
import app.dejv.impl.octarine.tool.selection.editmode.transform.TransformProgressFeedback;
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
    private Translate translate = new Translate();

    private final TransformProgressFeedback transformProgressFeedback;

    //private final Map<Shape, Point2D> transformationFeedback = new HashMap<>();


    public EditModeTranslate(Octarine octarine, TransformProgressFeedback transformProgressFeedback) {
        super(octarine, TranslateRequest.class);

        requireNonNull(transformProgressFeedback, "transformProgressFeedback is null");

        this.transformProgressFeedback = transformProgressFeedback;
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

            translate.setX(0);
            translate.setY(0);

            final Set<Shape> shapes = selection.stream().map(ControllerUtils::getShape).collect(Collectors.toSet());
            transformProgressFeedback.activate(shapes, translate);

            e.consume();

            getOctarine().getEditationListeners().forEach(EditationListener::onEditStarted);
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            translate.setX(e.getSceneX() - initialPosition.getX());
            translate.setY(e.getSceneY() - initialPosition.getY());
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {

            drag = false;

            transformProgressFeedback.deactivate();

            final Dimension2D positionDelta = new Dimension2D(e.getSceneX() - initialPosition.getX(), e.getSceneY() - initialPosition.getY());

            executeOnSelection(new TranslateRequest(new Translate(positionDelta.getWidth(), positionDelta.getHeight())));

            getOctarine().getEditationListeners().forEach(EditationListener::onEditFinished);
        }
    }
}
