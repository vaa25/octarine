package app.dejv.impl.octarine.tool.selection;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

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

    private final SelectionOutlinesStaticFeedback selectionOutlinesStaticFeedback;

    private ExclusiveEditMode activeExclusiveEditMode = null;
    private ExclusiveEditMode preferredExclusiveEditMode = null;


    private boolean isActive = false;


    public SelectionTool(Octarine octarine, List<EditMode> coexistingEditorModes, List<ExclusiveEditMode> exclusiveEditorModes, SelectionOutlinesStaticFeedback selectionOutlinesStaticFeedback) {
        requireNonNull(octarine, "octarine is null");
        requireNonNull(coexistingEditorModes, "coexistingEditorModes is null");
        requireNonNull(exclusiveEditorModes, "exclusiveEditorModes is null");
        requireNonNull(selectionOutlinesStaticFeedback, "selectionOutline is null");

        this.octarine = octarine;
        this.coexistingEditorModes = coexistingEditorModes;
        this.exclusiveEditorModes = exclusiveEditorModes;
        this.selectionOutlinesStaticFeedback = selectionOutlinesStaticFeedback;
    }


    @Override
    public void activate() {
        if (!isActive) {

            selectionOutlinesStaticFeedback.activate();

            octarine.getSelectionManager().addSelectionChangeListener(this);

            coexistingEditorModes.forEach(EditMode::activate);
            exclusiveEditorModes.forEach(ExclusiveEditMode::installActivationHandlers);

            SelectionManager selectionManager = octarine.getSelectionManager();
            List<Controller> currentSelection = selectionManager.getSelection();

            selectionChanged(selectionManager, currentSelection, currentSelection, new ArrayList<>());

            isActive = true;
        }
    }


    @Override
    public void deactivate() {
        if (isActive) {
            octarine.getSelectionManager().removeSelectionChangeListener(this);

            exclusiveEditorModes.parallelStream().forEach(ExclusiveEditMode::uninstallActivationHandlers);
            coexistingEditorModes.parallelStream().forEach(EditMode::deactivate);

            selectionOutlinesStaticFeedback.deactivate();

            isActive = false;
        }
    }


    @Override
    public void selectionChanged(SelectionManager sender, List<Controller> selection, List<Controller> added, List<Controller> removed) {
        LOG.trace("Selection changed: {}", selection.toString());

        selectionOutlinesStaticFeedback.selectionChanged(added, removed);

        coexistingEditorModes.stream().forEach(editor -> editor.selectionUpdated(selection));
        exclusiveEditorModes.stream().forEach(editor -> editor.selectionUpdated(selection));

        if ((activeExclusiveEditMode == null) || (!activeExclusiveEditMode.isEnabled())) {
            findEnabledExclusiveEditMode();
        }
    }


    @Override
    public void onActivationRequest(ExclusiveEditMode sender) {
        requireNonNull(sender, "sender is NULL");

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
