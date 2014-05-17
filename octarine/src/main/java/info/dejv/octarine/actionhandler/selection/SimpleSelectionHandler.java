/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine.actionhandler.selection;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.octarine.actionhandler.ActionHandler;
import info.dejv.octarine.actionhandler.feedback.MouseOverDynamicFeedback;
import info.dejv.octarine.actionhandler.selection.helpers.IncrementalSelectionHelper;
import info.dejv.octarine.actionhandler.selection.helpers.IncrementalSelectionListener;
import info.dejv.octarine.command.SelectCommand;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.request.shape.ShapeRequest;
import info.dejv.octarine.tool.Tool;
import info.dejv.octarine.tool.selection.SelectionTool;
import info.dejv.octarine.tool.selection.SelectionToolListener;

/**
 *
 * @author dejv
 * @param <T> Handled tool
 */
public class SimpleSelectionHandler<T extends Tool>
        extends ActionHandler
        implements IncrementalSelectionListener, SelectionToolListener {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleSelectionHandler.class);

    private final IncrementalSelectionHelper incrementalSelectionHelper;
    private boolean edited = false;

    @Autowired
    private SelectionTool selectionTool;


    public SimpleSelectionHandler(Class<T> toolClass, Controller controller) {
        super(toolClass, controller);

        if (!controller.supports(ShapeRequest.class)) {
            throw new IllegalArgumentException("Controller has to support ShapeRequest for SimpleSelectionHandler to work properly with it");
        }

        this.incrementalSelectionHelper = new IncrementalSelectionHelper(getOctarine(), this);

        controller.addRequestHandler(SelectableRequestHandler.getInstance());

        selectionTool.getListeners().add(this);
    }

    @Override
    public void toolActivated(Tool tool) {
        Node view = getController().getView();
        view.addEventHandler(MouseEvent.MOUSE_ENTERED, this::handleMouseEntered);
        view.addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        view.addEventHandler(MouseEvent.MOUSE_EXITED, this::handleMouseExited);
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
    }

    @Override
    public void toolDeactivated(Tool tool) {
        Node view = getController().getView();
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
        final SelectCommand sc = new SelectCommand(getOctarine().getSelectionManager(), SelectCommand.Op.ADD, getController());
        getOctarine().getCommandStack().execute(sc);
    }


    @Override
    public void removeFromSelection() {
        LOG.debug("Removing from selection");
        final SelectCommand sc = new SelectCommand(getOctarine().getSelectionManager(), SelectCommand.Op.REMOVE, getController());
        getOctarine().getCommandStack().execute(sc);
    }


    @Override
    public void replaceSelection() {
        // Don't try to select again, if already selected
        if (isAlreadySelected()) {
            return;
        }

        LOG.debug("Replacing selection");
        final SelectCommand sc = new SelectCommand(getOctarine().getSelectionManager(), SelectCommand.Op.REPLACE, getController());
        getOctarine().getCommandStack().execute(sc);
    }


    private void handleMouseEntered(MouseEvent e) {
        incrementalSelectionHelper.activate(e);
        MouseOverDynamicFeedback.add(getOctarine(), getController());
    }


    private void handleMouseMoved(MouseEvent e) {
        incrementalSelectionHelper.refresh(e);
    }


    private void handleMouseExited(MouseEvent e) {
        incrementalSelectionHelper.deactivate();
        MouseOverDynamicFeedback.remove();
    }


    private void handleMousePressed(MouseEvent e) {
        if (edited) {
            edited = false;
            return;
        }
        incrementalSelectionHelper.commit(e);
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
        return getOctarine().getSelectionManager().contains(getController());
    }
}
