package info.dejv.octarine.tool.selection.request;

import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.request.CommandRequest;
import java.util.Set;

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
