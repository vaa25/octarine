package info.dejv.octarine.actionhandler.selection;

import info.dejv.octarine.actionhandler.ActionHandler;
import info.dejv.octarine.actionhandler.selection.feedback.MarqueeSelectionDynamicFeedback;
import info.dejv.octarine.actionhandler.selection.helpers.IncrementalSelectionHelper;
import info.dejv.octarine.actionhandler.selection.helpers.IncrementalSelectionListener;
import info.dejv.octarine.command.SelectCommand;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.tool.Tool;
import info.dejv.octarine.tool.selection.SelectionTool;

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

/**
 *
 * @author dejv
 * @param <T>
 */
public class ContainerSelectionHandler
        extends ActionHandler
        implements IncrementalSelectionListener {

    private static final Logger LOG = LoggerFactory.getLogger(ContainerSelectionHandler.class);

    private boolean drag = false;
    private final ObservableList<Node> nodeList;

    private final IncrementalSelectionHelper incrementalSelectionHelper;


    public ContainerSelectionHandler(ContainerController controller, ObservableList<Node> nodeList) {
        super(SelectionTool.class, controller);
        this.nodeList = nodeList;
        incrementalSelectionHelper = new IncrementalSelectionHelper(getOctarine(), this);
    }

    @Override
    public ContainerController getController() {
        return (ContainerController) super.getController();
    }


    @Override
    public void toolActivated(Tool tool) {
        Node view = getController().getView();
        view.addEventHandler(MouseEvent.DRAG_DETECTED, this::handleDragDetected);
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }

    @Override
    public void toolDeactivated(Tool tool) {
        Node view = getController().getView();
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
            MarqueeSelectionDynamicFeedback.add(getOctarine(), new Point2D(e.getX(), e.getY()));
            incrementalSelectionHelper.activate(e);
            e.consume();
        }
    }


    private void handleMouseDragged(MouseEvent e) {
        if (drag) {
            MarqueeSelectionDynamicFeedback msdf = MarqueeSelectionDynamicFeedback.getInstance();
            if (msdf != null) {
                msdf.setCurrentCoords(new Point2D(e.getX(), e.getY()));
            }
            incrementalSelectionHelper.refresh(e);
        }
    }


    private void handleMouseReleased(MouseEvent e) {
        if (drag) {
            incrementalSelectionHelper.commit(e);
            MarqueeSelectionDynamicFeedback.remove();
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
            Controller controller = getController().lookup(c -> node.equals(c.getView()));
            if ((controller != null) && (controller.supports(Selectable.class))) {
                result.add(controller);
            }
        });
    }

    private boolean isFullyWithinBounds(Node node, Bounds bounds) {
        Bounds b = node.getBoundsInParent();
        return ((b.getMinX() > bounds.getMinX()) && (b.getMaxX() < bounds.getMaxX()) && (b.getMinY() > bounds.getMinY()) && (b.getMaxY() < bounds.getMaxY()));
    }

    private void performMarqueeSelection(SelectCommand.Op op) {
        MarqueeSelectionDynamicFeedback marqueeNode = MarqueeSelectionDynamicFeedback.getInstance();
        List<Controller> selectableChildren = (marqueeNode != null) ? getBoundedSelectables(MarqueeSelectionDynamicFeedback.getInstance().getBoundsInLocal()) : null;
        final SelectCommand sc = new SelectCommand(getOctarine().getSelectionManager(), op, selectableChildren);
        getOctarine().getCommandStack().execute(sc);
    }
}
