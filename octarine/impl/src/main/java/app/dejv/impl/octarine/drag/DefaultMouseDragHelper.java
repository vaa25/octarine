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
public class DefaultMouseDragHelper implements MouseDragHelper {

    private Node node;
    private MouseDragListener listener;

    private boolean drag;


    @Override
    public void activate(Node node, MouseDragListener listener) {
        requireNonNull(node, "node is null");
        requireNonNull(listener, "listener is null");

        this.node = node;
        this.listener = listener;

        node.addEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }


    @Override
    public void deactivate() {
        node.removeEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        node.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);

        node = null;
        listener = null;
    }


    private void handleDragDetected(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            drag = true;

            listener.onDragStarted(e.getX(), e.getY());
            e.consume();
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            listener.onDragged(e.getX(), e.getY());
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            drag = false;
            listener.onDragCommited(e.getX(), e.getY());
        } else {
            listener.onMouseReleased(e.getX(), e.getY());
        }
    }

}
