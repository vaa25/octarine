package info.dejv.octarine.tool.selection.extension;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.tool.Tool;
import info.dejv.octarine.tool.ToolExtension;
import info.dejv.octarine.tool.selection.SelectionTool;
import info.dejv.octarine.tool.selection.command.SelectCommand;
import info.dejv.octarine.tool.selection.extension.feedback.MarqueeSelectionDynamicFeedback;
import info.dejv.octarine.tool.selection.extension.helper.IncrementalSelectionListener;
import info.dejv.octarine.tool.selection.extension.helper.IncrementalSelectionManager;
import info.dejv.octarine.tool.selection.request.SelectRequest;

/**
 * Selection tool extension to enable proper "marquee selection" handling of container controllers.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
@Scope("prototype")
public class ContainerSelectionToolExtension
        extends ToolExtension
        implements IncrementalSelectionListener {

    private static final Logger LOG = LoggerFactory.getLogger(ContainerSelectionToolExtension.class);

    private final IncrementalSelectionManager incrementalSelectionManager;

    private final MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback;

    private ContainerController controller;

    private boolean drag = false;

    private ObservableList<Node> nodeList;

    public ContainerSelectionToolExtension(
            ContainerController controller,
            Octarine octarine, MarqueeSelectionDynamicFeedback marqueeSelectionDynamicFeedback, IncrementalSelectionManager incrementalSelectionManager) {

        super(SelectionTool.class, controller, octarine);

        requireNonNull(marqueeSelectionDynamicFeedback);
        requireNonNull(incrementalSelectionManager);

        this.controller = controller;
        this.marqueeSelectionDynamicFeedback = marqueeSelectionDynamicFeedback;
        this.incrementalSelectionManager = incrementalSelectionManager;

    }

    public ContainerSelectionToolExtension setNodeList(ObservableList<Node> nodeList) {
        this.nodeList = nodeList;
        return this;
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
            marqueeSelectionDynamicFeedback.add(new Point2D(e.getX(), e.getY()));
            incrementalSelectionManager.activate(e, this);
            e.consume();
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            marqueeSelectionDynamicFeedback.setCurrentCoords(new Point2D(e.getX(), e.getY()));
            incrementalSelectionManager.refresh(e);
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            incrementalSelectionManager.commit(e);
            marqueeSelectionDynamicFeedback.remove();
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
