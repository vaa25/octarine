package app.dejv.impl.octarine.model.chunk;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TranslationChunk {

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0d);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0d);

    private final BooleanProperty supportsTranslate = new SimpleBooleanProperty(false);


    public double getX() {
        return x.get();
    }


    public DoubleProperty xProperty() {
        return x;
    }


    public void setX(double x) {
        this.x.set(x);
    }


    public double getY() {
        return y.get();
    }


    public DoubleProperty yProperty() {
        return y;
    }


    public void setY(double y) {
        this.y.set(y);
    }


    public boolean getSupportsTranslate() {
        return supportsTranslate.get();
    }


    public BooleanProperty supportsTranslateProperty() {
        return supportsTranslate;
    }


    public void setSupportsTranslate(boolean supportsTranslate) {
        this.supportsTranslate.set(supportsTranslate);
    }
}
