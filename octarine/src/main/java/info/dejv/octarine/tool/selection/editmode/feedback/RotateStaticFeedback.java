package info.dejv.octarine.tool.selection.editmode.feedback;

import java.io.IOException;
import javax.annotation.PostConstruct;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.SVGPath;

import org.springframework.stereotype.Component;

import info.dejv.octarine.feedback.StaticFeedback;
import info.dejv.octarine.utils.CompositeObservableBounds;
import info.dejv.octarine.utils.ConstantZoomDoubleBinding;
import info.dejv.octarine.utils.FormattingUtils;

/**
 * Static feedback for "Rotate" edit mode
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
public class RotateStaticFeedback extends StaticFeedback {

    private Group pivotCross;

    @PostConstruct
    public void initRotateStaticFeedback() throws IOException {
        this.pivotCross = FXMLLoader.load(System.class.getResource("/fxml/rotpivot.fxml"));

        DoubleProperty zoomFactor = octarine.getViewer().zoomFactorProperty();
        ConstantZoomDoubleBinding pivotCrossScale = new ConstantZoomDoubleBinding(zoomFactor, 1.0);
        pivotCross.scaleXProperty().bind(pivotCrossScale);
        pivotCross.scaleYProperty().bind(pivotCrossScale);

        FormattingUtils.formatSymbol((SVGPath) pivotCross.lookup("#symbol"));
    }


    public void set(Point2D pivot, CompositeObservableBounds selectionBounds) {

    }

    public void clear() {
        
    }

}
