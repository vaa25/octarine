package info.dejv.octarine.request.handler;

import info.dejv.octarine.command.DeleteCommand;
import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.controller.RequestHandler;
import info.dejv.octarine.model.ModelElement;
import info.dejv.octarine.request.Request;
import info.dejv.octarine.tool.selection.request.DeleteRequest;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DeleteRequestHandler
        implements RequestHandler {

    private final ContainerController target;

    public DeleteRequestHandler(ContainerController controller) {
        this.target = controller;
    }


    @Override
    public boolean supports(Class<? extends Request> request) {
        return DeleteRequest.class.equals(request);
    }


    @Override
    public void request(Request request) {
        requireNonNull(request, "request is null");

        if (!DeleteRequest.class.equals(request.getClass())) {
            throw new IllegalArgumentException("Unsupported request: " + request);
        }

        DeleteRequest deleteRequest = (DeleteRequest) request;

        ObservableList<ModelElement> childrenList = target.getModel().getChildren().get();
        Set<ModelElement> modelElementsToDelete = deleteRequest.getControllersToDelete().stream()
                .map((controller) -> controller.getModel())
                .filter((model) -> childrenList.contains(model))
                .collect(Collectors.toSet());

        deleteRequest.setCommand(new DeleteCommand(modelElementsToDelete, target, target.getOctarine().getSelectionManager()));
    }

}
