/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine.tool.selection.editmode;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.tool.selection.request.TranslateRequest;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

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
            view.addEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
            view.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
            view.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        });
    }

    @Override
    public void deactivate() {
        selection.stream().map((item) -> item.getView()).forEach((view) -> {
            view.removeEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
            view.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
            view.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        });
    }


    private void handleDragDetected(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            drag = true;
            initialPosition = new Point2D(e.getX(), e.getY());
            // TODO:Add translate feedback
            e.consume();
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            // TODO:Update translate feedback
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            // TODO: Remove translate feedback, commit new location
            executeOnSelection(new TranslateRequest(e.getX() - initialPosition.getX(), e.getY() - initialPosition.getY()));
            drag = false;
        }
    }
}
