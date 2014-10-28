package app.dejv.impl.octarine.drag;

import static java.util.Objects.requireNonNull;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import app.dejv.octarine.input.MouseDragHelper;
import app.dejv.octarine.input.MouseDragListener;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DefaultMouseDragHelper
        implements MouseDragHelper {

    private Node node;
    private MouseDragListener listener;

    private boolean drag;
    private boolean isActive = false;


    @Override
    public void activate(Node node, MouseDragListener listener) {
        if (isActive) {
            return;
        }

        requireNonNull(node, "node is null");
        requireNonNull(listener, "listener is null");

        this.node = node;
        this.listener = listener;

        node.addEventFilter(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        node.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        node.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);

        isActive = true;
    }


    @Override
    public void deactivate() {
        if (!isActive) {
            return;
        }

        node.removeEventFilter(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        node.removeEventFilter(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        node.removeEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);

        node = null;
        listener = null;
        isActive = false;
    }


    public Node getNode() {
        return node;
    }


    private void handleDragDetected(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            drag = true;

            dragStarted(e);

            listener.onDragStarted(e.getSceneX(), e.getSceneY());
            e.consume();
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            dragging(e);

            listener.onDragged(e.getSceneX(), e.getSceneY());
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            dragFinished(e);

            drag = false;
            listener.onDragCommited(e.getSceneX(), e.getSceneY());
        } else {
            listener.onMouseReleased(e.getSceneX(), e.getSceneY());
        }
    }

    protected void dragStarted(MouseEvent e) {

    }

    protected void dragging(MouseEvent e) {

    }

    protected void dragFinished(MouseEvent e) {

    }
}
