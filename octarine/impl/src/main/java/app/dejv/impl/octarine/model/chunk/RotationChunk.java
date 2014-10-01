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

    private final DoubleProperty pivotX = new SimpleDoubleProperty(this, "pivotX", 0d);
    private final DoubleProperty pivotY = new SimpleDoubleProperty(this, "pivotY", 0d);

    private final BooleanProperty supportsRotate = new SimpleBooleanProperty(false);


    public double getAngle() {
        return angle.get();
    }


    public DoubleProperty angleProperty() {
        return angle;
    }


    public void setAngle(double angle) {
        this.angle.set(angle);
    }


    public double getPivotX() {
        return pivotX.get();
    }


    public DoubleProperty pivotXProperty() {
        return pivotX;
    }


    public void setPivotX(double pivotX) {
        this.pivotX.set(pivotX);
    }


    public double getPivotY() {
        return pivotY.get();
    }


    public DoubleProperty pivotYProperty() {
        return pivotY;
    }


    public void setPivotY(double pivotY) {
        this.pivotY.set(pivotY);
    }


    public boolean getSupportsRotate() {
        return supportsRotate.get();
    }


    public BooleanProperty supportsRotateProperty() {
        return supportsRotate;
    }


    public void setSupportsRotate(boolean supportsRotate) {
        this.supportsRotate.set(supportsRotate);
    }
}
