package app.dejv.impl.octarine.tool.selection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.impl.octarine.utils.CompositeObservableBounds;
import app.dejv.impl.octarine.utils.ConstantZoomDoubleBinding;
import app.dejv.impl.octarine.utils.FormattingUtils;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackOpacity;
import app.dejv.impl.octarine.utils.FormattingUtils.FeedbackType;
import app.dejv.octarine.controller.Controller;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class SelectionOutlines {

    private static final Logger LOG = LoggerFactory.getLogger(SelectionOutlines.class);
    private static final double SELECTION_BOX_OFFSET = 1.0d;

    private final Map<Controller, Shape> activeOutlines = new HashMap<>();

    private final ObservableList<Node> feedbackLayer;

    private final ConstantZoomDoubleBinding offset;
    private final ConstantZoomDoubleBinding sizeGrowth;


    public SelectionOutlines(ObservableList<Node> feedbackLayer, DoubleProperty zoomFactor) {
        this.feedbackLayer = feedbackLayer;

        offset = new ConstantZoomDoubleBinding(zoomFactor, SELECTION_BOX_OFFSET);
        sizeGrowth = new ConstantZoomDoubleBinding(zoomFactor, 2 * SELECTION_BOX_OFFSET);
    }


    void clear() {
        activeOutlines.keySet().forEach(this::removeOutline);
    }


    public void selectionChanged(List<Controller> added, List<Controller> removed) {

        if (removed != null) {
            removed.forEach(this::removeOutline);
        }

        if (added != null) {
            added.forEach(this::addOutline);
        }
    }


    private void removeOutline(Controller controller) {
        LOG.debug("Outline remove {}", controller.getId());
        assert activeOutlines.containsKey(controller) : "Controller was reported to be removed from selection, but doesn't have an outline";

        Shape outline = activeOutlines.get(controller);

        feedbackLayer.remove(outline);
        activeOutlines.remove(controller);
    }


    private void addOutline(Controller controller) {
        LOG.debug("Outline add {}", controller.getId());

        assert !activeOutlines.containsKey(controller) : "Controller was reported to be added to selection, but already has an outline";

        Shape outline = createOutline(controller.getView(), "[" + controller.getId() + "] Selection box");

        activeOutlines.put(controller, outline);
        feedbackLayer.add(outline);
    }


    private Rectangle createOutline(Node view, String id) {

        final Rectangle outline = new Rectangle();
        FormattingUtils.formatFeedbackOutline(outline, FeedbackType.STATIC, FeedbackOpacity.OPAQUE, id);

        bindOutline(outline, view);

        return outline;
    }


    private void bindOutline(Rectangle outline, Node view) {
        final CompositeObservableBounds nodeBounds = new CompositeObservableBounds(view.boundsInParentProperty());

        outline.xProperty().bind(nodeBounds.minXProperty().subtract(offset));
        outline.yProperty().bind(nodeBounds.minYProperty().subtract(offset));
        outline.widthProperty().bind(nodeBounds.widthProperty().add(sizeGrowth));
        outline.heightProperty().bind(nodeBounds.heightProperty().add(sizeGrowth));
        outline.strokeWidthProperty().bind(FormattingUtils.getDefaultFeedbackStrokeWidth(FormattingUtils.FeedbackType.STATIC));
    }
}
