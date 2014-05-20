package info.dejv.octarine.tool.selection.editmode;

import static java.util.Objects.requireNonNull;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.ExclusiveEditMode;
import info.dejv.octarine.tool.selection.ExclusivityCoordinator;

/**
 * Common code for "exclusive" edit modes
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class AbstractExclusiveEditMode
        extends AbstractEditMode
        implements ExclusiveEditMode {

    private ExclusivityCoordinator exclusivityCoordinator;

    public AbstractExclusiveEditMode(Class<? extends Request> requestType) {
        super(requestType);
    }


    public AbstractExclusiveEditMode setExclusivityCoordinator(ExclusivityCoordinator exclusivityCoordinator) {
        requireNonNull(exclusivityCoordinator, "exclusivityCoordinator is NULL");

        this.exclusivityCoordinator = exclusivityCoordinator;
        return this;
    }


    @Override
    protected void tryActivate() {
        if (isActive()) {
            doActivate();
        }
    }

    @Override
    public void installActivationHandlers() {
        LOG.trace("Installing activation handler on: {}", scene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }


    @Override
    public void uninstallActivationHandlers() {
        LOG.trace("Uninstalling activation handler from: {}", scene);
        scene.removeEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }


    protected final void handleKeyPressed(KeyEvent e) {
        LOG.debug("Key pressed: {}", e.getCode());
        final KeyCode code = getActivationKey();
        requireNonNull(code, "getActivationKey() returns NULL");

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
        if (!isEnabled()) {
            return;
        }
        LOG.debug("Requesting activation: {}", this.getClass().getSimpleName());
        exclusivityCoordinator.onActivationRequest(this);
    }


    protected abstract KeyCode getActivationKey();
}
