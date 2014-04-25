package info.dejv.octarine.tool.selection.editmode;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.command.CommandStack;
import info.dejv.octarine.command.CompoundCommand;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.request.CommandRequest;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.EditMode;
import info.dejv.octarine.tool.selection.TransformListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common code for typical edit modes
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractEditMode
        implements EditMode {

    protected enum HandlePos {

        N,
        NE,
        E,
        SE,
        S,
        SW,
        W,
        NW
    }

    protected final Logger LOG;
    protected final Scene scene;
    protected final CommandStack commandStack;

    private final List<TransformListener> listeners = new ArrayList<>();
    protected final Set<Controller> selection = new HashSet<>();

    protected final Class<? extends Request> requestType;

    private boolean enabled = true;

    public AbstractEditMode(Class<? extends Request> requestType, Octarine octarine) {
        Objects.requireNonNull(requestType, "type is NULL");
        Objects.requireNonNull(octarine, "octarine is NULL");
        this.requestType = requestType;

        this.scene = octarine.getNode().getScene();
        this.commandStack = octarine.getCommandStack();

        assert this.scene != null : "scene is NULL";
        assert this.commandStack != null : "commandStack is NULL";

        LOG = LoggerFactory.getLogger(getClass());
    }


    @Override
    public void addListener(TransformListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    protected void notifyTransformationStarted() {
        listeners.stream().forEach(listener -> listener.transformationStarted());
    }

    protected void notifyTransformationFinished() {
        listeners.stream().forEach(listener -> listener.transformationFinished());
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }


    protected void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public void selectionUpdated(List<Controller> newSelection) {
        assert newSelection != null : "newSelection is NULL";

        beforeSelectionUpdated();

        enabled = (newSelection.size() > 0);
        selection.clear();

        newSelection.stream().forEach(controller -> {
            if (isSelectionItemSupported(controller)) {
                selection.add(controller);
            } else {
                enabled = false;
            }
        });

        if (!enabled) {
            selection.clear();
        }
        LOG.debug("{} on current selection", enabled ? "Enabled" : "Disabled");
        afterSelectionUpdated();
    }


    /**
     * Override to react on selection update
     */
    protected void beforeSelectionUpdated() {
        // Blank
    }


    /**
     * Override to react on selection update
     */
    protected void afterSelectionUpdated() {
        // Blank
    }


    protected boolean isSelectionItemSupported(Controller controller) {
        return controller.supports(requestType);
    }


    protected void executeOnSelection(CommandRequest cr) {
        executeOnSet(cr, selection);
    }


    protected void executeOnSet(CommandRequest cr, Set<Controller> targets) {
        CompoundCommand compoundCommand = new CompoundCommand();

        targets.stream().forEach(requestTarget -> {
            cr.setCommand(null);
            requestTarget.request(cr);

            if (cr.getCommand() == null) {
                throw new IllegalArgumentException("Controller " + requestTarget + ": Expected to set Command inside " + cr + ", but it didn't.");
            }
            compoundCommand.addCommand(cr.getCommand());
        });

        commandStack.execute(compoundCommand);
    }
}