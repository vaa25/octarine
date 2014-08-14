package app.dejv.impl.octarine.request.handler;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import app.dejv.impl.octarine.tool.selection.command.DeleteCommand;
import app.dejv.impl.octarine.tool.selection.request.DeleteRequest;
import app.dejv.octarine.controller.ContainerController;
import app.dejv.octarine.controller.Controller;
import app.dejv.octarine.model.ModelElement;
import app.dejv.octarine.request.Request;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DeleteRequestHandler
        extends AbstractRequestHandler {

    private final ContainerController target;

    public DeleteRequestHandler(ContainerController target) {
        requireNonNull(target, "target is null");

        this.target = target;
    }

    @Override
    public boolean supports(Class<? extends Request> request) {
        return DeleteRequest.class.equals(request);
    }


    @Override
    protected void requestChecked(Request request) {

        DeleteRequest deleteRequest = (DeleteRequest) request;

        ObservableList<ModelElement> childrenList = target.getModel().getChildren().get();
        Set<ModelElement> modelElementsToDelete = deleteRequest.getControllersToDelete().stream()
                .map(Controller::getModel)
                .filter(childrenList::contains)
                .collect(Collectors.toSet());

        deleteRequest.setCommand(new DeleteCommand(modelElementsToDelete, target, target.getOctarine().getSelectionManager()));
    }
}
