package app.dejv.impl.octarine.request;

import app.dejv.octarine.command.Command;
import app.dejv.octarine.request.Request;

/**
 * A parent class for "command requests" (Requests to return command)
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class CommandRequest
        implements Request {

    private Command command;

    /**
     * @return The command, that was set as an answer to the request
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Set the requested command<br/>
     * <b>Note: A consumer of CommandRequest is supposed to feed the requested command using this method</b>
     * @param requestedCommand Command to answer the request with
     */
    public void setCommand(Command requestedCommand) {
        this.command = requestedCommand;
    }

}
