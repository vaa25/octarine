/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine.tool.selection.editmode;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.SVGPath;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.tool.selection.ExclusivityCoordinator;
import info.dejv.octarine.tool.selection.request.RotateRequest;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;
import info.dejv.octarine.utils.FormattingUtils;

/**
 * "Rotate" edit mode<br/>
 * On handle drag it rotates the selection around the specified pivot point
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class EditModeRotate
        extends AbstractExclusiveEditMode {

    private final Group pivotCross;
    private final ConstantZoomDoubleBinding pivotCrossScale;

    private final ObservableList<Node> feedback;


    public EditModeRotate(Octarine octarine, ExclusivityCoordinator listener) throws IOException {
        super(RotateRequest.class, octarine, listener);

        this.pivotCross = FXMLLoader.load(System.class.getResource("/fxml/rotpivot.fxml"));
        this.feedback = octarine.getFeedback();

        DoubleProperty zoomFactor = octarine.getViewer().zoomFactorProperty();
        pivotCrossScale = new ConstantZoomDoubleBinding(zoomFactor, 1.0);
        pivotCross.scaleXProperty().bind(pivotCrossScale);
        pivotCross.scaleYProperty().bind(pivotCrossScale);

        FormattingUtils.formatGlow((SVGPath) pivotCross.lookup("#glow"));
        FormattingUtils.formatSymbol((SVGPath) pivotCross.lookup("#symbol"), true);
    }


    @Override
    protected KeyCode getActivationKey() {
        return KeyCode.R;
    }

    @Override
    protected void doActivate() {
        feedback.add(pivotCross);
        System.out.println("PIVOT " + pivotCross.getBoundsInParent());
    }

    @Override
    protected void doDeactivate() {
        feedback.remove(pivotCross);
    }

}
