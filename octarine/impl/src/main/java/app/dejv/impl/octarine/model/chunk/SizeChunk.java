package app.dejv.impl.octarine.model.chunk;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Dimension2D;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class SizeChunk {

    private final DoubleProperty width = new SimpleDoubleProperty(this, "width", 0d);
    private final DoubleProperty height = new SimpleDoubleProperty(this, "height", 0d);

    private final BooleanProperty supportsResize = new SimpleBooleanProperty(false);


    public SizeChunk() {
    }


    public SizeChunk(double initialX, double initialY) {
        set(initialX, initialY);
    }


    public DoubleProperty widthProperty() {
        return width;
    }


    public double getWidth() {
        return width.doubleValue();
    }


    public SizeChunk setWidth(double newWidth) {
        this.width.set(newWidth);
        return this;
    }


    public DoubleProperty heightProperty() {
        return height;
    }


    public double getHeight() {
        return height.doubleValue();
    }


    public SizeChunk setHeight(double newHeight) {
        this.height.set(newHeight);
        return this;
    }


    public final Dimension2D get() {
        return new Dimension2D(getWidth(), getHeight());
    }


    public final void set(double newWidth, double newHeight) {
        setWidth(newWidth);
        setHeight(newHeight);
    }


    public final void set(Dimension2D newSize) {
        set(newSize.getWidth(), newSize.getHeight());
    }


    public boolean getSupportsResize() {
        return supportsResize.get();
    }


    public SizeChunk setSupportsResize(boolean supportsResize) {
        this.supportsResize.set(supportsResize);
        return this;
    }


    public BooleanProperty supportsResizeProperty() {
        return supportsResize;
    }
}
