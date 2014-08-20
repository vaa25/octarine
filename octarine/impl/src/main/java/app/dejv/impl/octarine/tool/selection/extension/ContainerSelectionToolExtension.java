package app.dejv.impl.octarine.tool.selection.extension;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.impl.octarine.tool.selection.SelectionTool;
import app.dejv.impl.octarine.tool.selection.command.SelectCommand;
import app.dejv.impl.octarine.tool.selection.command.SelectCommand.Op;
import app.dejv.impl.octarine.tool.selection.extension.helper.MarqueeSelectionActionListener;
import app.dejv.impl.octarine.tool.selection.extension.helper.MarqueeSelectionManager;
import app.dejv.impl.octarine.tool.selection.request.SelectRequest;
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
        implements MarqueeSelectionActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerSelectionToolExtension.class);
    private final MarqueeSelectionManager marqueeSelectionManager;
    private ContainerController controller;
    private ObservableList<Node> nodeList;


    public ContainerSelectionToolExtension(ContainerController controller, Octarine octarine,  ObservableList<Node> nodeList,
                                           MarqueeSelectionManager marqueeSelectionManager) {

        super(SelectionTool.class, controller, octarine);

        requireNonNull(nodeList);
        requireNonNull(marqueeSelectionManager);

        this.controller = controller;
        this.nodeList = nodeList;

        this.marqueeSelectionManager = marqueeSelectionManager;
    }


    @Override
    public void toolActivated(Tool tool) {
        marqueeSelectionManager.activate(controller.getView(), this);
    }


    @Override
    public void toolDeactivated(Tool tool) {
        marqueeSelectionManager.deactivate();
    }


    @Override
    public void addToSelection(Bounds marqueeBounds) {
        performMarqueeSelection(Op.ADD, marqueeBounds);
    }


    @Override
    public void removeFromSelection(Bounds marqueeBounds) {
        performMarqueeSelection(Op.REMOVE, marqueeBounds);
    }


    @Override
    public void replaceSelection(Bounds marqueeBounds) {
        performMarqueeSelection(Op.REPLACE, marqueeBounds);
    }


    @Override
    public void deselectAll() {
        performMarqueeSelection(Op.DESELECT_ALL, null);
    }


    private List<Controller> getBoundedSelectables(Bounds marqueeBounds) {
        final List<Controller> result = new ArrayList<>();

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

            final Controller candidate = controller.lookup(c -> node.equals(c.getView()));

            if ((candidate != null) && (candidate.supports(SelectRequest.class))) {
                result.add(candidate);
            }
        });
    }


    private boolean isFullyWithinBounds(Node node, Bounds bounds) {
        final Bounds b = node.getBoundsInParent();
        return ((b.getMinX() > bounds.getMinX()) &&
                (b.getMaxX() < bounds.getMaxX()) &&
                (b.getMinY() > bounds.getMinY()) &&
                (b.getMaxY() < bounds.getMaxY()));
    }


    private void performMarqueeSelection(SelectCommand.Op op, Bounds marqueeBounds) {
        final List<Controller> selectableChildren = (marqueeBounds != null) ? getBoundedSelectables(marqueeBounds) : null;
        final SelectCommand sc = new SelectCommand(octarine.getSelectionManager(), op, selectableChildren);
        octarine.getCommandStack().execute(sc);
    }
}
