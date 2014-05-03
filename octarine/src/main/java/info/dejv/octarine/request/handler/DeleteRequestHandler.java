package info.dejv.octarine.request.handler;

import info.dejv.octarine.command.DeleteCommand;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.request.DeleteRequest;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DeleteRequestHandler
        extends AbstractRequestHandler {

    private final ContainerController target;

    public DeleteRequestHandler(ContainerController controller) {
        this.target = controller;
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
                .map((controller) -> controller.getModel())
                .filter((model) -> childrenList.contains(model))
                .collect(Collectors.toSet());

        deleteRequest.setCommand(new DeleteCommand(modelElementsToDelete, target, target.getOctarine().getSelectionManager()));
    }

}
