package app.dejv.impl.octarine.tool.selection.request;

import java.util.Set;

import app.dejv.impl.octarine.request.CommandRequest;
import app.dejv.octarine.controller.Controller;

/**
 * Request to return "DELETE" command
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class DeleteRequest
        extends CommandRequest {

    private final Set<Controller> controllersToDelete;

    public DeleteRequest(Set<Controller> controllersToDelete) {
        this.controllersToDelete = controllersToDelete;
    }

    public Set<Controller> getControllersToDelete() {
        return controllersToDelete;
    }
}
