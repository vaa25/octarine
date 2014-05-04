package info.dejv.octarine.utils;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class CompositeBounds
        extends AbstractObservableBounds {

    private final List<ReadOnlyObjectProperty<Bounds>> sourceBounds = new ArrayList<>();

    public CompositeBounds() {
        minX.set(10);
        minY.set(10);
        minZ.set(10);
        maxX.set(20);
        maxY.set(20);
        maxZ.set(20);
    }

    public final CompositeBounds add(ReadOnlyObjectProperty<Bounds> nodeBounds) {
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
        };
    }
}
