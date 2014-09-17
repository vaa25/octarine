package app.dejv.impl.octarine.model.chunk;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TransformChunk {

    private final ObjectProperty<Transform> transform = new SimpleObjectProperty<>(new Affine());

    private final BooleanProperty supportsTranslate = new SimpleBooleanProperty(false);
    private final BooleanProperty supportsScale = new SimpleBooleanProperty(false);
    private final BooleanProperty supportsRotate = new SimpleBooleanProperty(false);

    public Transform getTransform() {
        return transform.get();
    }


    public ObjectProperty<Transform> transformProperty() {
        return transform;
    }


    public TransformChunk setTransform(Transform transform) {
        this.transform.set(transform);
        return this;
    }


    public TransformChunk clearTransform() {
        transform.setValue(new Affine());
        return this;
    }


    public TransformChunk addTransform(Transform transform) {
        this.transform.setValue(this.transform.getValue().createConcatenation(transform));
        return this;
    }


    public boolean getSupportsTranslate() {
        return supportsTranslate.get();
    }


    public BooleanProperty supportsTranslateProperty() {
        return supportsTranslate;
    }


    public TransformChunk setSupportsTranslate(boolean supportsTranslate) {
        this.supportsTranslate.set(supportsTranslate);
        return this;
    }


    public boolean getSupportsScale() {
        return supportsScale.get();
    }


    public BooleanProperty supportsScaleProperty() {
        return supportsScale;
    }


    public TransformChunk setSupportsScale(boolean supportsScale) {
        this.supportsScale.set(supportsScale);
        return this;
    }


    public boolean getSupportsRotate() {
        return supportsRotate.get();
    }


    public BooleanProperty supportsRotateProperty() {
        return supportsRotate;
    }


    public TransformChunk setSupportsRotate(boolean supportsRotate) {
        this.supportsRotate.set(supportsRotate);
        return this;
    }
}
