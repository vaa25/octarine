package app.dejv.impl.octarine.tool.selection;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.octarine.command.Command;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.selection.SelectionManager;

/**
 *
 * @author dejv
 */
public class SelectCommand
        implements Command {

    public enum Op {

        REPLACE,
        ADD,
        REMOVE,
        DESELECT_ALL
    }


    private static final Logger LOG = LoggerFactory.getLogger(SelectCommand.class);
    private final SelectionManager selection;
    private final Op op;
    private final List<Controller> selectables;
    private List<Controller> previousSelection;


    public SelectCommand(SelectionManager selection, Op op, Controller selectable) {
        this(selection, op, (selectable == null) ? null : Arrays.asList(selectable));
    }


    public SelectCommand(SelectionManager selectionManager, Op op, List<Controller> selectables) {
        requireNonNull(selectionManager, "selectionManager is null");
        requireNonNull(op, "op is null");

        if (op != Op.DESELECT_ALL) {
            requireNonNull(selectables, "selectables is null");
        }

        this.selection = selectionManager;
        this.op = op;
        this.selectables = selectables;
    }


    @Override
    public void execute() {
        LOG.info("Executing {} on {}", op, selectables);
        previousSelection = selection.getSelection();

        switch (op) {
            case REPLACE: {
                selection.replace(selectables);
                break;
            }
            case ADD: {
                selection.add(selectables);
                break;
            }
            case REMOVE: {
                selection.remove(selectables);
                break;
            }
            default: {
                selection.deselectAll();
            }
        }
    }


    @Override
    public void undo() {
        LOG.info("Undoing");
        selection.replace(previousSelection);
    }
}
