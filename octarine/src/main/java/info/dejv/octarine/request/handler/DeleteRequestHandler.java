package info.dejv.octarine.request.handler;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import info.dejv.octarine.tool.selection.command.DeleteCommand;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.stereotype.RequestHandler;
import info.dejv.octarine.tool.selection.request.DeleteRequest;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
@RequestHandler
public class DeleteRequestHandler
        extends AbstractRequestHandler {

    private ContainerController target;

    public DeleteRequestHandler setTarget(ContainerController target) {
        this.target = target;
        return this;
    }

    @Override
    public boolean supports(Class<? extends Request> request) {
        return DeleteRequest.class.equals(request);
    }


    @Override
    protected void requestChecked(Request request) {
        requireNonNull(target, "target is null");

        DeleteRequest deleteRequest = (DeleteRequest) request;

        ObservableList<ModelElement> childrenList = target.getModel().getChildren().get();
        Set<ModelElement> modelElementsToDelete = deleteRequest.getControllersToDelete().stream()
                .map(Controller::getModel)
                .filter(childrenList::contains)
                .collect(Collectors.toSet());

        deleteRequest.setCommand(new DeleteCommand(modelElementsToDelete, target, target.getOctarine().getSelectionManager()));
    }
}
