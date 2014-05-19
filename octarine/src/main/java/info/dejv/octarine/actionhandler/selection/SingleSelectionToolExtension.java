/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dejv.octarine.actionhandler.selection;

import static java.util.Objects.requireNonNull;

import javax.annotation.PostConstruct;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import info.dejv.octarine.actionhandler.ToolExtension;
import info.dejv.octarine.actionhandler.feedback.MouseOverDynamicFeedback;
import info.dejv.octarine.actionhandler.selection.helpers.IncrementalSelectionListener;
import info.dejv.octarine.actionhandler.selection.helpers.IncrementalSelectionManager;
import info.dejv.octarine.command.SelectCommand;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.request.shape.ShapeRequest;
import info.dejv.octarine.tool.Tool;
import info.dejv.octarine.tool.selection.SelectionTool;
import info.dejv.octarine.tool.selection.SelectionToolListener;

/**
 * Selection tool extension to enable proper "single selection" handling of selectable controllers.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@Component
@Scope("prototype")
public class SingleSelectionToolExtension
        extends ToolExtension
        implements IncrementalSelectionListener, SelectionToolListener {

    private static final Logger LOG = LoggerFactory.getLogger(SingleSelectionToolExtension.class);

    @Autowired
    private MouseOverDynamicFeedback mouseOverDynamicFeedback;

    @Autowired
    private IncrementalSelectionManager incrementalSelectionManager;

    @Autowired
    private SelectableRequestHandler selectableRequestHandler;

    @Autowired
    private SelectionTool selectionTool;

    private boolean edited = false;



    public SingleSelectionToolExtension() {
        super(SelectionTool.class);
    }


    @Override
    public SingleSelectionToolExtension setController(Controller controller) {

        if (!controller.supports(ShapeRequest.class)) {
            throw new IllegalArgumentException("Controller has to support ShapeRequest for SingleSelectionToolExtension to work properly with it");
        }

        controller.addRequestHandler(selectableRequestHandler);

        super.setController(controller);
        return this;
    }


    @PostConstruct
    public void initSingleSelectionToolExtension() {
        selectionTool.getListeners().add(this);
    }


    @Override
    public void toolActivated(Tool tool) {
        requireNonNull(controller, "controller is null");

        Node view = controller.getView();
        view.addEventHandler(MouseEvent.MOUSE_ENTERED, this::handleMouseEntered);
        view.addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        view.addEventHandler(MouseEvent.MOUSE_EXITED, this::handleMouseExited);
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
    }

    @Override
    public void toolDeactivated(Tool tool) {
        requireNonNull(controller, "controller is null");

        Node view = controller.getView();
        view.removeEventHandler(MouseEvent.MOUSE_ENTERED, this::handleMouseEntered);
        view.removeEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        view.removeEventHandler(MouseEvent.MOUSE_EXITED, this::handleMouseExited);
        view.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
    }

    @Override
    public void addToSelection() {
        requireNonNull(controller, "controller is null");

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
        requireNonNull(controller, "controller is null");

        LOG.debug("Removing from selection");
        final SelectCommand sc = new SelectCommand(octarine.getSelectionManager(), SelectCommand.Op.REMOVE, controller);
        octarine.getCommandStack().execute(sc);
    }


    @Override
    public void replaceSelection() {
        requireNonNull(controller, "controller is null");

        // Don't try to select again, if already selected
        if (isAlreadySelected()) {
            return;
        }

        LOG.debug("Replacing selection");
        final SelectCommand sc = new SelectCommand(octarine.getSelectionManager(), SelectCommand.Op.REPLACE, controller);
        octarine.getCommandStack().execute(sc);
    }


    private void handleMouseEntered(MouseEvent e) {
        requireNonNull(controller, "controller is null");

        incrementalSelectionManager.activate(e, this);
        mouseOverDynamicFeedback.add(controller);
    }


    private void handleMouseMoved(MouseEvent e) {
        incrementalSelectionManager.refresh(e);
    }


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
