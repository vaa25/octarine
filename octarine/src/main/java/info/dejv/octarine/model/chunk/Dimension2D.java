package info.dejv.octarine.model.chunk;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class Dimension2D {

    private final DoubleProperty width = new SimpleDoubleProperty(0.0d);
    private final DoubleProperty height = new SimpleDoubleProperty(0.0d);

    public Dimension2D() {

    }


    public Dimension2D(double initialWidth, double initialHeight) {
        set(initialWidth, initialHeight);
    }


    public DoubleProperty getWidth() {
        return width;
    }


    private void setWidth(double newWidth) {
        width.set(newWidth);
    }


    public DoubleProperty getHeight() {
        return height;
    }


    private void setHeight(double newHeight) {
        height.set(newHeight);
    }


    public final void set(double newWidth, double newHeight) {
        setWidth(newWidth);
        setHeight(newHeight);
    }
}
