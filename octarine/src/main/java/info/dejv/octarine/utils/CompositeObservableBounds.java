package info.dejv.octarine.utils;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;

import org.springframework.stereotype.Component;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class CompositeObservableBounds
        extends AbstractObservableBounds {

    private final List<ReadOnlyObjectProperty<Bounds>> sourceBounds = new ArrayList<>();

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
                minX.set(bounds.getMinX());
                minY.set(bounds.getMinY());
                minZ.set(bounds.getMinZ());
                maxX.set(bounds.getMaxX());
                maxY.set(bounds.getMaxY());
                maxZ.set(bounds.getMaxZ());
                initialized = true;
            } else {
                minX.set(Double.min(minX.get(), bounds.getMinX()));
                minY.set(Double.min(minY.get(), bounds.getMinY()));
                minZ.set(Double.min(minZ.get(), bounds.getMinZ()));
                maxX.set(Double.max(maxX.get(), bounds.getMaxX()));
                maxY.set(Double.max(maxY.get(), bounds.getMaxY()));
                maxZ.set(Double.max(maxZ.get(), bounds.getMaxZ()));
            }
        }
    }
}
