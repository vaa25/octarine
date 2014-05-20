package info.dejv.octarine.tool.selection.editmode;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.command.CommandStack;
import info.dejv.octarine.command.CompoundCommand;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.request.CommandRequest;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.EditMode;

/**
 * Common code for typical edit modes
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractEditMode
        implements EditMode {

    protected final Logger LOG;
    protected final Set<Controller> selection = new HashSet<>();

    protected final Class<? extends Request> requestType;

    protected Scene scene;
    protected CommandStack commandStack;

    private boolean enabled = true;
    private boolean active = false;

    @Autowired
    private Octarine octarine;

    public AbstractEditMode(Class<? extends Request> requestType) {
        Objects.requireNonNull(requestType, "type is NULL");

        this.requestType = requestType;

        LOG = LoggerFactory.getLogger(getClass());
    }


    /**
     * Initailize the Edit Mode.
     * Call this method when "Scene" is available, not sooner!
     */
    public void init() {
        this.scene = octarine.getViewer().getScene();
        this.commandStack = octarine.getCommandStack();

        assert this.scene != null : "scene is NULL";
        assert this.commandStack != null : "commandStack is NULL";
    }

    @Override
    public final void activate() {
        if (!active) {
            doActivate();
            active = true;
        }
    }

    @Override
    public final void deactivate() {
        if (active) {
            doDeactivate();
            active = false;
        }
    }

    @Override
    public void selectionUpdated(List<Controller> newSelection) {
        assert newSelection != null : "newSelection is NULL";

        doDeactivate();

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
            active = false;
            selection.clear();
        } else {
            tryActivate();
        }
        LOG.debug("{} on current selection", enabled ? "Enabled" : "Disabled");
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean isActive() {
        return active;
    }

    public Octarine getOctarine() {
        return octarine;
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

    protected void tryActivate() {
            doActivate();
    }

    protected abstract void doActivate();

    protected abstract void doDeactivate();

}
