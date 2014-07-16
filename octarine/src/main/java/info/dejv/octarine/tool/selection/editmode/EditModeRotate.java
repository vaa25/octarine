/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine.tool.selection.editmode;

import java.util.stream.Stream;
import javax.annotation.PostConstruct;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.dejv.octarine.tool.selection.editmode.feedback.RotateStaticFeedback;
import info.dejv.octarine.tool.selection.request.RotateRequest;
import info.dejv.octarine.utils.CompositeObservableBounds;

/**
 * "Rotate" edit mode<br/>
 * On handle drag it rotates the selection around the specified pivot point
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class EditModeRotate
        extends AbstractExclusiveEditMode {

    @Autowired
    private CompositeObservableBounds selectionBounds;

    @Autowired
    private RotateStaticFeedback staticFeedback;


    public EditModeRotate() {
        super(RotateRequest.class);
    }


    @PostConstruct
    public void initEditModeRotate() {
    }


    @Override
    public EditModeRotate setExclusivityCoordinator(ExclusivityCoordinator exclusivityCoordinator) {
        return (EditModeRotate) super.setExclusivityCoordinator(exclusivityCoordinator);
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.R;
    }

    @Override
    protected void doActivate() {
        selectionBounds.clear();

        final Stream<ReadOnlyObjectProperty<Bounds>> boundsStream = selection.stream()
                .map((controller) -> controller.getView().boundsInParentProperty());

        staticFeedback.show(null, boundsStream);
    }

    @Override
    protected void doDeactivate() {
        staticFeedback.hide();
        selectionBounds.clear();
    }

}
