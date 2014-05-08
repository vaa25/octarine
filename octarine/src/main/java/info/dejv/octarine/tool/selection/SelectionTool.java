package info.dejv.octarine.tool.selection;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.selection.SelectionChangeListener;
import info.dejv.octarine.selection.SelectionManager;
import info.dejv.octarine.tool.Tool;
import info.dejv.octarine.tool.selection.editmode.EditModeDelete;
import info.dejv.octarine.tool.selection.editmode.EditModeResize;
import info.dejv.octarine.tool.selection.editmode.EditModeRotate;
import info.dejv.octarine.tool.selection.editmode.EditModeTranslate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SelectionTool
        implements Tool, SelectionChangeListener, ExclusivityCoordinator {

    private static SelectionTool instance;

    private static final Logger LOG = LoggerFactory.getLogger(SelectionTool.class);

    private final SelectionOutlines selectionOutlines;

    private final Octarine octarine;

    private final List<SelectionToolListener> listeners = new ArrayList<>();

    //Editors, that coexist with each other (ie. Delete, Translate)
    private final List<EditMode> coexistingEditorModes = new ArrayList<>();

    //Editors, that can only be selected one at a time (ie. Scale, Rotate)
    private final List<ExclusiveEditMode> exclusiveEditModes = new ArrayList<>();

    private ExclusiveEditMode activeExclusiveEditMode = null;

    private boolean initiated = false;
    private boolean active = false;


    public static void initialize(Octarine octarine) {
        if (instance == null) {
            instance = new SelectionTool(octarine);
        }
    }

    public static SelectionTool getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SelectionTool was not yet initialized. Call initialize(octarine)");
        }
        return instance;
    }


    private SelectionTool(Octarine octarine) {
        this.octarine = octarine;
        this.selectionOutlines = new SelectionOutlines(octarine.getFeedback(), octarine.getViewer().zoomFactorProperty());

        Node pane = octarine.getViewer();

        if (pane.getScene() != null) {  // If Scene is already available, initiate now...
            initiate();
        } else {                        //.. otherwise initiate, when it is set
            pane.sceneProperty().addListener((sender, oldValue, newValue) -> initiate());
        }
    }


    public List<SelectionToolListener> getListeners() {
        return listeners;
    }


    public Octarine getOctarine() {
        return octarine;
    }


    public final void initiate() {
        coexistingEditorModes.add(new EditModeDelete(octarine));
        coexistingEditorModes.add(new EditModeTranslate(octarine));

        exclusiveEditModes.add(new EditModeResize(octarine, this));
        exclusiveEditModes.add(new EditModeRotate(octarine, this));

        initiated = true;
        if (active) {
            doActivate();
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


    private void doActivate() {
        octarine.getSelectionManager().addSelectionChangeListener(this);

        coexistingEditorModes.parallelStream().forEach(editor -> editor.activate());
        exclusiveEditModes.parallelStream().forEach(editor -> editor.installActivationHandlers());

        SelectionManager selectionManager = octarine.getSelectionManager();
        List<Controller> currentSelection = selectionManager.getSelection();
        selectionChanged(selectionManager, currentSelection, currentSelection, new ArrayList<>());
    }

    private void doDeactivate() {
        octarine.getSelectionManager().removeSelectionChangeListener(this);

        exclusiveEditModes.parallelStream().forEach(editor -> editor.uninstallActivationHandlers());
        coexistingEditorModes.parallelStream().forEach(editor -> editor.deactivate());

        final ObservableList<Node> fb = octarine.getFeedback();
        selectionOutlines.clear();
    }


    @Override
    public void selectionChanged(SelectionManager sender, List<Controller> selection, List<Controller> added, List<Controller> removed) {
        LOG.trace("Selection changed: {}", selection.toString());

        selectionOutlines.selectionChanged(added, removed);

        coexistingEditorModes.stream().forEach(editor -> editor.selectionUpdated(selection));
        exclusiveEditModes.stream().forEach(editor -> editor.selectionUpdated(selection));

        if ((activeExclusiveEditMode == null) || (!activeExclusiveEditMode.isEnabled())) {
            findEnabledExclusiveEditMode();
        }
    }


    @Override
    public void onActivationRequest(ExclusiveEditMode sender) {
        Objects.requireNonNull(sender, "sender is NULL");

        setActiveExclusiveEditMode(sender);
    }


    private void findEnabledExclusiveEditMode() {
        activeExclusiveEditMode = null;
        for (ExclusiveEditMode mode : exclusiveEditModes) {
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
