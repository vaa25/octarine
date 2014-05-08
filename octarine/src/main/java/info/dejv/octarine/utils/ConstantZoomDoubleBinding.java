package info.dejv.octarine.utils;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;

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

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        super.addListener(listener); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addListener(InvalidationListener listener) {
        super.addListener(listener); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        super.removeListener(listener); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        super.removeListener(listener); //To change body of generated methods, choose Tools | Templates.
    }


}
