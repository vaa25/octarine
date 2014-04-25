package info.dejv.octarine.tool.selection.editmode;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.ExclusiveEditMode;
import info.dejv.octarine.tool.selection.ExclusivityCoordinator;
import java.util.Objects;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Common code for "exclusive" edit modes
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractExclusiveEditMode
        extends AbstractEditMode
        implements ExclusiveEditMode {

    private final ExclusivityCoordinator exclusivityCoordinator;

    public AbstractExclusiveEditMode(Class<? extends Request> requestType, Octarine octarine, ExclusivityCoordinator exclusivityCoordinator) {
        super(requestType, octarine);

        Objects.requireNonNull(exclusivityCoordinator, "exclusivityCoordinator is NULL");

        this.exclusivityCoordinator = exclusivityCoordinator;
    }


    @Override
    public void installActivationHandlers() {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
    }


    @Override
    public void uninstallActivationHandlers() {
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
    }


    protected final void handleKeyReleased(KeyEvent e) {
        KeyCode code = getActivationKey();
        Objects.requireNonNull(code, "getActivationKey() returns NULL");

        if (!code.equals(e.getCode())) {
            return;
        }

        if (e.isAltDown() || e.isControlDown() || e.isMetaDown() || e.isShiftDown() || e.isShortcutDown()) {
            return;
        }

        e.consume();

        requestActivation();
    }


    protected final void requestActivation() {
        LOG.trace("Requesting activation");
        exclusivityCoordinator.onActivationRequest(this);
    }


    protected abstract KeyCode getActivationKey();
}
