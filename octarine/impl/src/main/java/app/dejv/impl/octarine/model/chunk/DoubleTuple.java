package app.dejv.impl.octarine.model.chunk;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DoubleTuple {

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x");
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y");


    public DoubleTuple(double initialX, double initialY) {
        set(initialX, initialY);
    }


    public DoubleProperty xProperty() {
        return x;
    }


    public double getX() {
        return x.doubleValue();
    }


    public void setX(double newX) {
        this.x.set(newX);
    }


    public DoubleProperty yProperty() {
        return y;
    }


    public double getY() {
        return y.doubleValue();
    }


    public void setY(double newY) {
        this.y.set(newY);
    }


    public final void set(double newX, double newY) {
        setX(newX);
        setY(newY);
    }
}
