package app.dejv.impl.octarine.tool.selection.editmode.resize;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;

import app.dejv.impl.octarine.tool.selection.editmode.AbstractExclusiveEditMode;
import app.dejv.impl.octarine.tool.selection.editmode.TransformationListener;
import app.dejv.impl.octarine.utils.ControllerUtils;
import app.dejv.octarine.Octarine;

/**
 * "Scale" edit mode<br/>
 * On handles drag it scales the selection in appropriate direction
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeResize
        extends AbstractExclusiveEditMode
        implements TransformationListener {

    private ResizeHandleFeedback resizeHandleFeedback;
    private ResizeProgressManager resizeProgressManager;


    public EditModeResize(Octarine octarine, ResizeHandleFeedback resizeHandleFeedback, ResizeProgressManager resizeProgressManager) {
        super(octarine, ResizeRequest.class);
        requireNonNull(resizeHandleFeedback, "resizeHandleFeedback is null");
        requireNonNull(resizeProgressManager, "resizeProgressManager is null");

        this.resizeHandleFeedback = resizeHandleFeedback;
        this.resizeProgressManager = resizeProgressManager;
    }


    @Override
    public void transformationCommited(Transform transform) {
        executeOnSelection(new ResizeRequest(transform));
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.S;
    }


    @Override
    protected void doActivate() {
        final Set<Shape> shapes = new HashSet<>();

        selection.forEach((controller) -> {
            Shape shape = ControllerUtils.getShape(controller);
            shapes.add(shape);
        });

        resizeHandleFeedback.activate();
        resizeProgressManager.activate(shapes, this);
    }


    @Override
    protected void doDeactivate() {
        resizeHandleFeedback.deactivate();
        resizeProgressManager.deactivate();
    }
}
