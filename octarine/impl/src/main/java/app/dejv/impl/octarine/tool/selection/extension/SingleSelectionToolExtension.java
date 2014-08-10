/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dejv.impl.octarine.tool.selection.extension;

import static java.util.Objects.requireNonNull;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dejv.impl.octarine.request.shape.ShapeRequest;
import app.dejv.impl.octarine.tool.selection.SelectionTool;
import app.dejv.impl.octarine.tool.selection.command.SelectCommand;
import app.dejv.impl.octarine.tool.selection.extension.feedback.MouseOverDynamicFeedback;
import app.dejv.impl.octarine.tool.selection.extension.helper.IncrementalSelectionListener;
import app.dejv.impl.octarine.tool.selection.extension.helper.IncrementalSelectionManager;
import app.dejv.impl.octarine.tool.selection.request.SelectRequest;
import app.dejv.octarine.Octarine;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.tool.EditationListener;
import app.dejv.octarine.tool.Tool;
import app.dejv.octarine.tool.ToolExtension;

/**
 * Selection tool extension to enable proper "single selection" handling of selectable controllers.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class SingleSelectionToolExtension
        extends ToolExtension
        implements IncrementalSelectionListener, EditationListener {

    private static final Logger LOG = LoggerFactory.getLogger(SingleSelectionToolExtension.class);

    private final MouseOverDynamicFeedback mouseOverDynamicFeedback;
    private final IncrementalSelectionManager incrementalSelectionManager;

    private boolean edited = false;


    public SingleSelectionToolExtension(
            Controller controller,
            Octarine octarine,  MouseOverDynamicFeedback mouseOverDynamicFeedback, IncrementalSelectionManager incrementalSelectionManager) {

        super(SelectionTool.class, controller, octarine);

        requireNonNull(mouseOverDynamicFeedback);
        requireNonNull(incrementalSelectionManager);

        this.mouseOverDynamicFeedback = mouseOverDynamicFeedback;
        this.incrementalSelectionManager = incrementalSelectionManager;

        if (!controller.supports(ShapeRequest.class)) {
            throw new IllegalArgumentException("Controller has to support ShapeRequest for SingleSelectionToolExtension to work properly with it");
        }

        supportedRequests.add(SelectRequest.class);

        octarine.getEditationListeners().add(this);
    }


    @Override
    public void toolActivated(Tool tool) {
        Node view = controller.getView();
        view.addEventHandler(MouseEvent.MOUSE_ENTERED, this::handleMouseEntered);
        view.addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        view.addEventHandler(MouseEvent.MOUSE_EXITED, this::handleMouseExited);
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
    }

    @Override
    public void toolDeactivated(Tool tool) {
        Node view = controller.getView();
        view.removeEventHandler(MouseEvent.MOUSE_ENTERED, this::handleMouseEntered);
        view.removeEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        view.removeEventHandler(MouseEvent.MOUSE_EXITED, this::handleMouseExited);
        view.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
    }

    @Override
    public void addToSelection() {

        // Don't try to select again, if already selected
        if (isAlreadySelected()) {
            return;
        }

        LOG.debug("Adding to selection");

        final SelectCommand sc = new SelectCommand(octarine.getSelectionManager(), SelectCommand.Op.ADD, controller);
        octarine.getCommandStack().execute(sc);
    }


    @Override
    public void removeFromSelection() {
        LOG.debug("Removing from selection");

        final SelectCommand sc = new SelectCommand(octarine.getSelectionManager(), SelectCommand.Op.REMOVE, controller);
        octarine.getCommandStack().execute(sc);
    }


    @Override
    public void replaceSelection() {

        // Don't try to select again, if already selected
        if (isAlreadySelected()) {
            return;
        }

        LOG.debug("Replacing selection");

        final SelectCommand sc = new SelectCommand(octarine.getSelectionManager(), SelectCommand.Op.REPLACE, controller);
        octarine.getCommandStack().execute(sc);
    }


    private void handleMouseEntered(MouseEvent e) {
        incrementalSelectionManager.activate(e, this);
        mouseOverDynamicFeedback.add(controller);
    }


    private void handleMouseMoved(MouseEvent e) {
        incrementalSelectionManager.refresh(e);
    }


    @SuppressWarnings("UnusedParameters")
    private void handleMouseExited(MouseEvent e) {
        incrementalSelectionManager.deactivate();
        mouseOverDynamicFeedback.remove();
    }


    private void handleMousePressed(MouseEvent e) {
        if (edited) {
            edited = false;
            return;
        }
        incrementalSelectionManager.commit(e);
    }

    @Override
    public void onEditStarted() {
        edited = true;
    }

    @Override
    public void onEditFinished() {
        edited = false;
    }


    private boolean isAlreadySelected() {
        return octarine.getSelectionManager().contains(controller);
    }
}
