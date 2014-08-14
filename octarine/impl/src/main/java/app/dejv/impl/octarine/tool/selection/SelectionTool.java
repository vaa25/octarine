package app.dejv.impl.octarine.tool.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.selection.SelectionChangeListener;
import app.dejv.octarine.selection.SelectionManager;
import app.dejv.octarine.tool.Tool;
import app.dejv.octarine.tool.editmode.EditMode;
import app.dejv.octarine.tool.editmode.ExclusiveEditMode;
import app.dejv.octarine.tool.editmode.ExclusivityCoordinator;


public class SelectionTool
        implements Tool, SelectionChangeListener, ExclusivityCoordinator {

    private static final Logger LOG = LoggerFactory.getLogger(SelectionTool.class);

    private Octarine octarine;

    //Editors, that coexist with each other (ie. Delete, Translate)
    private final List<EditMode> coexistingEditorModes;

    //Editors, that can only be selected one at a time (ie. Scale, Rotate)
    private final List<ExclusiveEditMode> exclusiveEditorModes;

    private ExclusiveEditMode activeExclusiveEditMode = null;
    private ExclusiveEditMode preferredExclusiveEditMode = null;

    private SelectionOutlines selectionOutlines;

    private boolean initiated = false;
    private boolean active = false;


    public SelectionTool(Octarine octarine, List<EditMode> coexistingEditorModes, List<ExclusiveEditMode> exclusiveEditorModes) {
        this.octarine = octarine;
        this.coexistingEditorModes = coexistingEditorModes;
        this.exclusiveEditorModes = exclusiveEditorModes;

        final Node pane = octarine.getViewer();

        if (pane.getScene() != null) {  // If Scene is already available, initiate now...
            initWithSceneAvailable();
        } else {                        //.. otherwise initiate, when it is set
            pane.sceneProperty().addListener((sender, oldValue, newValue) -> initWithSceneAvailable());
        }
    }


    @Override
    public void activate() {
        if (initiated) {
            doActivate();
        }

        active = true;
    }


    @Override
    public void deactivate() {
        active = false;

        if (initiated) {
            doDeactivate();
        }
    }



    private void initWithSceneAvailable() {
        selectionOutlines = new SelectionOutlines(octarine.getGroupFeedbackStatic(), octarine.getViewer().zoomFactorProperty());

        initiated = true;
        if (active) {
            doActivate();
        }
    }


    private void doActivate() {
        if (!initiated)
            return;

        octarine.getSelectionManager().addSelectionChangeListener(this);

        coexistingEditorModes.forEach(editMode -> {
            editMode.initWithSceneAvailable();
            editMode.activate();
        });
        exclusiveEditorModes.forEach(editMode -> {
            editMode.initWithSceneAvailable();
            editMode.installActivationHandlers();
        });

        SelectionManager selectionManager = octarine.getSelectionManager();
        List<Controller> currentSelection = selectionManager.getSelection();
        selectionChanged(selectionManager, currentSelection, currentSelection, new ArrayList<>());
    }


    private void doDeactivate() {
        octarine.getSelectionManager().removeSelectionChangeListener(this);

        exclusiveEditorModes.parallelStream().forEach(ExclusiveEditMode::uninstallActivationHandlers);
        coexistingEditorModes.parallelStream().forEach(EditMode::deactivate);

        selectionOutlines.clear();
    }


    @Override
    public void selectionChanged(SelectionManager sender, List<Controller> selection, List<Controller> added, List<Controller> removed) {
        LOG.trace("Selection changed: {}", selection.toString());

        selectionOutlines.selectionChanged(added, removed);

        coexistingEditorModes.stream().forEach(editor -> editor.selectionUpdated(selection));
        exclusiveEditorModes.stream().forEach(editor -> editor.selectionUpdated(selection));

        if ((activeExclusiveEditMode == null) || (!activeExclusiveEditMode.isEnabled())) {
            findEnabledExclusiveEditMode();
        }
    }


    @Override
    public void onActivationRequest(ExclusiveEditMode sender) {
        Objects.requireNonNull(sender, "sender is NULL");

        preferredExclusiveEditMode = sender;

        LOG.debug("Activating: {}", sender.getClass().getSimpleName());
        setActiveExclusiveEditMode(sender);
    }


    private void findEnabledExclusiveEditMode() {
        activeExclusiveEditMode = null;

        if ((preferredExclusiveEditMode != null) &&(preferredExclusiveEditMode.isEnabled())) {
            setActiveExclusiveEditMode(preferredExclusiveEditMode);
            return;
        }

        for (ExclusiveEditMode mode : exclusiveEditorModes) {
            if (mode.isEnabled()) {
                setActiveExclusiveEditMode(mode);
                break;
            }
        }
    }


    private void setActiveExclusiveEditMode(ExclusiveEditMode exclusiveEditMode) {
        if (exclusiveEditMode.equals(activeExclusiveEditMode)) {
            return;
        }

        if (activeExclusiveEditMode != null) {
            activeExclusiveEditMode.deactivate();
        }

        activeExclusiveEditMode = exclusiveEditMode;
        activeExclusiveEditMode.activate();
    }
}
