package info.dejv.octarine.tool;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.controller.RequestHandler;
import info.dejv.octarine.request.Request;

/**
 * Controller-side tool extension.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class ToolExtension
        implements RequestHandler {

    protected final Controller controller;
    protected final Octarine octarine;
    protected final List<Class<? extends Request>> supportedRequests = new ArrayList<>();
    private final Class<? extends Tool> toolClass;


    public ToolExtension(Class<? extends Tool> toolClass, Controller controller, Octarine octarine) {
        requireNonNull(toolClass, "toolClass is null");
        requireNonNull(controller, "controller is null");
        requireNonNull(octarine, "octarine is null");

        this.toolClass = toolClass;
        this.controller = controller;
        this.octarine = octarine;
    }


    public void register() {
        octarine.addActionHandler(toolClass, this);
    }


    public void unregister() {
        octarine.removeActionHandler(toolClass, this);
    }

    @Override
    public boolean supports(Class<? extends Request> request) {
        return supportedRequests.contains(request);
    }

    @Override
    public void request(Request request) {
        // By default, it's not necesary to do anything
    }

    @SuppressWarnings("UnusedParameters")
    public abstract void toolActivated(Tool tool);


    @SuppressWarnings("UnusedParameters")
    public abstract void toolDeactivated(Tool tool);
}
