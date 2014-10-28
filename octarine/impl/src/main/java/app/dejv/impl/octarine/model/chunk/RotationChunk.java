package app.dejv.impl.octarine.model.chunk;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class RotationChunk {

    private final DoubleProperty angle = new SimpleDoubleProperty(this, "angle", 0d);

    private final DoubleProperty pivotX = new SimpleDoubleProperty(this, "pivotX", Double.MIN_VALUE);
    private final DoubleProperty pivotY = new SimpleDoubleProperty(this, "pivotY", Double.MIN_VALUE);

    private final BooleanProperty supportsRotate = new SimpleBooleanProperty(false);


    public double getAngle() {
        return angle.get();
    }


    public DoubleProperty angleProperty() {
        return angle;
    }


    public RotationChunk setAngle(double angle) {
        this.angle.set(angle);
        return this;
    }


    public double getPivotX() {
        return pivotX.get();
    }


    public DoubleProperty pivotXProperty() {
        return pivotX;
    }


    public RotationChunk setPivotX(double pivotX) {
        this.pivotX.set(pivotX);
        return this;
    }


    public double getPivotY() {
        return pivotY.get();
    }


    public DoubleProperty pivotYProperty() {
        return pivotY;
    }


    public RotationChunk setPivotY(double pivotY) {
        this.pivotY.set(pivotY);
        return this;
    }


    public boolean getSupportsRotate() {
        return supportsRotate.get();
    }


    public BooleanProperty supportsRotateProperty() {
        return supportsRotate;
    }


    public RotationChunk setSupportsRotate(boolean supportsRotate) {
        this.supportsRotate.set(supportsRotate);
        return this;
    }
}
