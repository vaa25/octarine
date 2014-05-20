package info.dejv.octarine.utils;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ConstantZoomDoubleBinding
        extends DoubleBinding {

    private final DoubleProperty zoomFactor;
    private final double value;

    public ConstantZoomDoubleBinding(DoubleProperty zoomFactor, double value) {
        this.zoomFactor = zoomFactor;
        this.value = value;
        super.bind(this.zoomFactor);
    }

    @Override
    protected double computeValue() {
        return value / zoomFactor.get();
    }
}
