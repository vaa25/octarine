package app.dejv.impl.octarine.utils;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class CompositeObservableBounds
        extends AbstractObservableBounds {

    private final List<ReadOnlyObjectProperty<Bounds>> sourceBounds = new ArrayList<>();
    private boolean rounded = false;


    public CompositeObservableBounds() {
        minX.set(0);
        minY.set(0);
        minZ.set(0);
        maxX.set(0);
        maxY.set(0);
        maxZ.set(0);
    }


    public CompositeObservableBounds(ReadOnlyObjectProperty<Bounds> nodeBounds) {
        add(nodeBounds);
    }


    public CompositeObservableBounds setRounded(boolean rounded) {
        this.rounded = rounded;
        return this;
    }


    @SuppressWarnings("UnusedReturnValue")
    public final CompositeObservableBounds add(ReadOnlyObjectProperty<Bounds> nodeBounds) {
        requireNonNull(nodeBounds, "nodeBounds is null");

        if (!sourceBounds.contains(nodeBounds)) {
            sourceBounds.add(nodeBounds);

            observedBoundsChanged(nodeBounds, null, nodeBounds.get());
            nodeBounds.addListener(this::observedBoundsChanged);
        }
        return this;
    }


    public void clear() {
        sourceBounds.stream().forEach((bounds) -> bounds.removeListener(this::observedBoundsChanged));
        sourceBounds.clear();
    }


    public void observedBoundsChanged(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
        update();
    }


    private void update() {
        boolean initialized = false;

        for (ReadOnlyObjectProperty<Bounds> property : sourceBounds) {
            Bounds bounds = property.get();

            if (!initialized) {
                minX.set(floorIfNeeded(bounds.getMinX()));
                minY.set(floorIfNeeded(bounds.getMinY()));
                minZ.set(floorIfNeeded(bounds.getMinZ()));
                maxX.set(ceilIfNeeded(bounds.getMaxX()));
                maxY.set(ceilIfNeeded(bounds.getMaxY()));
                maxZ.set(ceilIfNeeded(bounds.getMaxZ()));
                initialized = true;
            } else {
                minX.set(Double.min(minX.get(), floorIfNeeded(bounds.getMinX())));
                minY.set(Double.min(minY.get(), floorIfNeeded(bounds.getMinY())));
                minZ.set(Double.min(minZ.get(), floorIfNeeded(bounds.getMinZ())));
                maxX.set(Double.max(maxX.get(), ceilIfNeeded(bounds.getMaxX())));
                maxY.set(Double.max(maxY.get(), ceilIfNeeded(bounds.getMaxY())));
                maxZ.set(Double.max(maxZ.get(), ceilIfNeeded(bounds.getMaxZ())));
            }
        }
    }


    private double floorIfNeeded(double value) {
        return (rounded) ? Math.floor(value) : value;
    }


    private double ceilIfNeeded(double value) {
        return (rounded) ? Math.ceil(value) : value;
    }


    @Override
    public String toString() {
        return "CO bounds: [" + getMinX() + ", " + getMinY() + ", " + getMinZ() + "][" + getMaxX() + ", " + getMaxY() + ", " + getMaxZ() + "]";
    }
}
