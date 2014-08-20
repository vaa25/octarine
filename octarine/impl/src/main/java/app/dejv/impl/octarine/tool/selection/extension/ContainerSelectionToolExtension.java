package app.dejv.impl.octarine.tool.selection.extension;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.impl.octarine.tool.selection.SelectionTool;
import app.dejv.impl.octarine.tool.selection.command.SelectCommand;
import app.dejv.impl.octarine.tool.selection.extension.feedback.MarqueeSelectionDynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.helper.IncrementalSelectionListener;
import app.dejv.impl.octarine.tool.selection.extension.helper.IncrementalSelectionManager;
import app.dejv.impl.octarine.tool.selection.request.SelectRequest;
import app.dejv.impl.octarine.utils.MathUtils;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.tool.Tool;
import app.dejv.octarine.tool.ToolExtension;

/**
 * Selection tool extension to enable proper "marquee selection" handling of container controllers.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ContainerSelectionToolExtension
        extends ToolExtension
        implements IncrementalSelectionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerSelectionToolExtension.class);

    private final IncrementalSelectionManager incrementalSelectionManager;
    private final MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback;

    private ContainerController controller;
    private ObservableList<Node> nodeList;

    private boolean drag = false;
    private double initialX;
    private double initialY;

    //TODO: Derive standalone "MarqueeSelectionManager"
    public ContainerSelectionToolExtension(
            ContainerController controller, Octarine octarine,
            ObservableList<Node> nodeList, MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback, IncrementalSelectionManager incrementalSelectionManager) {

        super(SelectionTool.class, controller, octarine);

        requireNonNull(nodeList);
        requireNonNull(marqueeSelectionDynamicFeedback);
        requireNonNull(incrementalSelectionManager);

        this.controller = controller;
        this.nodeList = nodeList;
        this.marqueeSelectionDynamicFeedback = marqueeSelectionDynamicFeedback;
        this.incrementalSelectionManager = incrementalSelectionManager;

    }


    @Override
    public void toolActivated(Tool tool) {
        Node view = controller.getView();
        view.addEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }


    @Override
    public void toolDeactivated(Tool tool) {
        Node view = controller.getView();
        view.removeEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        view.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        view.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }


    @Override
    public void addToSelection() {
        performMarqueeSelection(SelectCommand.Op.ADD);
    }


    @Override
    public void removeFromSelection() {
        performMarqueeSelection(SelectCommand.Op.REMOVE);
    }


    @Override
    public void replaceSelection() {
        performMarqueeSelection(SelectCommand.Op.REPLACE);
    }


    private void handleDragDetected(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            drag = true;

            initialX = e.getX();
            initialY = e.getY();

            marqueeSelectionDynamicFeedback.setInitialCoords(initialX, initialY);

            marqueeSelectionDynamicFeedback.activate();
            incrementalSelectionManager.activate(this, true, true);
            e.consume();
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            marqueeSelectionDynamicFeedback.setCurrentCoords(e.getX(), e.getY());

            incrementalSelectionManager.setFeedbackLocation(MathUtils.mean(initialX, e.getX()), MathUtils.mean(initialY, e.getY()));
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            incrementalSelectionManager.commit();
            incrementalSelectionManager.deactivate();
            marqueeSelectionDynamicFeedback.deactivate();
            drag = false;
        } else {
            performMarqueeSelection(SelectCommand.Op.DESELECT_ALL);

        }
    }


    private List<Controller> getBoundedSelectables(Bounds marqueeBounds) {
        List<Controller> result = new ArrayList<>();
        filterSelectables(nodeList, result, marqueeBounds);
        return result;
    }


    private void filterSelectables(ObservableList<Node> list, List<Controller> result, Bounds marqueeBounds) {
        list.stream().forEach((node) -> {
            if (node instanceof Parent) {
                filterSelectables(((Parent) node).getChildrenUnmodifiable(), result, marqueeBounds);
            }

            if (!isFullyWithinBounds(node, marqueeBounds)) {
                return;
            }

            Controller candidate = controller.lookup(c -> node.equals(c.getView()));

            if ((candidate != null) && (candidate.supports(SelectRequest.class))) {
                result.add(candidate);
            }
        });
    }


    private boolean isFullyWithinBounds(Node node, Bounds bounds) {
        Bounds b = node.getBoundsInParent();
        return ((b.getMinX() > bounds.getMinX()) && (b.getMaxX() < bounds.getMaxX()) && (b.getMinY() > bounds.getMinY()) && (b.getMaxY() < bounds.getMaxY()));
    }


    private void performMarqueeSelection(SelectCommand.Op op) {
        List<Controller> selectableChildren = getBoundedSelectables(marqueeSelectionDynamicFeedback.getBoundsInLocal());
        final SelectCommand sc = new SelectCommand(octarine.getSelectionManager(), op, selectableChildren);
        octarine.getCommandStack().execute(sc);
    }
}
