package app.dejv.impl.octarine.drag;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;

import app.dejv.octarine.input.MouseDragHelper;
import app.dejv.octarine.input.MouseDragListener;
import app.dejv.octarine.input.SimpleMouseDragListener;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class PropertyMouseDragWrapper
        implements MouseDragListener {


    private final SimpleMouseDragListener listener;

    private final DoubleProperty x = new SimpleDoubleProperty(0);
    private final DoubleProperty y = new SimpleDoubleProperty(0);

    private double initialX;
    private double initialY;

    public PropertyMouseDragWrapper(MouseDragHelper helper, Node node, SimpleMouseDragListener listener) {
        requireNonNull(helper, "helper is null");
        requireNonNull(node, "node is null");
        requireNonNull(listener, "listener is null");

        this.listener = listener;
        helper.activate(node, this);
    }


    @Override
    public void onDragStarted(double initialX, double initialY) {
        this.initialX = initialX;
        this.initialY = initialY;

        listener.onDragStarted();
    }


    @Override
    public void onDragged(double currentX, double currentY) {
        x.setValue(currentX - initialX);
        y.setValue(currentY - initialY);
    }


    @Override
    public void onDragCancelled(double finalX, double finalY) {
        x.setValue(0);
        y.setValue(0);

        listener.onDragFinished();
    }


    @Override
    public void onDragCommited(double finalX, double finalY) {
        listener.onDragFinished();
    }


    @Override
    public void onMouseReleased(double finalX, double finalY) {

    }


    public double getX() {
        return x.get();
    }


    public ReadOnlyDoubleProperty xProperty() {
        return x;
    }


    public double getY() {
        return y.get();
    }


    public ReadOnlyDoubleProperty yProperty() {
        return y;
    }
}
