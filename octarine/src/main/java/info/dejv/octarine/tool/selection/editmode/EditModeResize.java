package info.dejv.octarine.tool.selection.editmode;

import javax.annotation.PostConstruct;

import javafx.scene.input.KeyCode;

import org.springframework.stereotype.Component;

import info.dejv.octarine.tool.selection.request.ResizeRequest;
import info.dejv.octarine.utils.CompositeObservableBounds;

/**
 * "Scale" edit mode<br/>
 * On handle drag it scales the selection in appropriate direction
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class EditModeResize
        extends AbstractExclusiveEditMode {

    private final CompositeObservableBounds selectionBounds = new CompositeObservableBounds();


    public EditModeResize() {
        super(ResizeRequest.class);

    }


    @PostConstruct
    public void initEditModeResize() {
    }


    @Override
    public EditModeResize setExclusivityCoordinator(ExclusivityCoordinator exclusivityCoordinator) {
        return (EditModeResize) super.setExclusivityCoordinator(exclusivityCoordinator);
    }

    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.S;
    }


    @Override
    protected void doActivate() {
        selectionBounds.clear();

        selection.forEach((controller) -> selectionBounds.add(controller.getView().boundsInParentProperty()));
    }


    @Override
    protected void doDeactivate() {
        selectionBounds.clear();
    }
}
