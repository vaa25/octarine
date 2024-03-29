package app.dejv.impl.octarine.tool.selection.editmode.transform;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.cfg.OctarineProps;
import app.dejv.impl.octarine.feedback.dynamics.DynamicFeedback;
import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.impl.octarine.utils.ConstantZoomDoubleBinding;
import app.dejv.octarine.Octarine;

/**
 * Resize Progress Feedback visually reflects the progress of "resize" operation in realtime.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class TransformProgressFeedback
        extends DynamicFeedback {

    public static final double RECT_STROKE_WIDTH = 1.0d;
    final DoubleProperty zoom;
    private Group shapesGroup = new Group();
    private Set<Shape> shapes;
    private Rectangle rectangle;
    private CompositeObservableBounds progressBounds = new CompositeObservableBounds().setRounded(true);

    private Transform transform;


    public TransformProgressFeedback(Octarine octarine) {
        super(octarine);
        zoom = octarine.getView().zoomFactorProperty();

        createResizeBoundBox();

        getChildren().add(shapesGroup);
        getChildren().add(rectangle);
    }


    @Override
    public void activate() {
        throw new UnsupportedOperationException("Call the appropriate version of activate");
    }


    public void activate(Set<Shape> shapes, Transform transform) {
        requireNonNull(shapes, "shapes is null");
        requireNonNull(transform, "transform is null");

        deactivate();

        this.shapes = shapes;
        this.transform = transform;

        super.activate();
    }


    @Override
    protected void beforeActivate() {
        super.beforeActivate();

        shapes.forEach((shape) -> {
            shape.setOpacity(0.5);
            shapesGroup.getChildren().add(shape);
        });
    }


    @Override
    protected void afterDeactivate() {
        shapesGroup.getChildren().clear();

        super.afterDeactivate();
    }


    @Override
    protected void bind() {
        super.bind();
        shapesGroup.getTransforms().add(transform);
        progressBounds.add(shapesGroup.boundsInParentProperty());
        rectangle.strokeWidthProperty().bind(new ConstantZoomDoubleBinding(zoom, RECT_STROKE_WIDTH));
        rectangle.xProperty().bind(progressBounds.minXProperty().subtract(0.5d));
        rectangle.yProperty().bind(progressBounds.minYProperty().subtract(0.5d));
        rectangle.widthProperty().bind(progressBounds.widthProperty().add(0.5d));
        rectangle.heightProperty().bind(progressBounds.heightProperty().add(0.5d));
    }


    @Override
    protected void unbind() {
        rectangle.strokeWidthProperty().unbind();
        rectangle.xProperty().unbind();
        rectangle.yProperty().unbind();
        rectangle.widthProperty().unbind();
        rectangle.heightProperty().unbind();
        progressBounds.clear();
        shapesGroup.getTransforms().remove(transform);
        super.unbind();
    }


    private void createResizeBoundBox() {
        rectangle = new Rectangle();
        rectangle.getStrokeDashArray().addAll(5d, 3d);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(OctarineProps.getInstance().getDynamicFeedbackColor());
    }

}
