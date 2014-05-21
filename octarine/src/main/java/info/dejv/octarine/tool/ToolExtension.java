package info.dejv.octarine.tool;

import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;

/**
 * Controller-side tool extension.
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public abstract class ToolExtension {

    private final Class<? extends Tool> toolClass;

    @Autowired
    protected Octarine octarine;

    protected Controller controller;


    public ToolExtension(Class<? extends Tool> toolClass) {
        this.toolClass = toolClass;
    }


    public ToolExtension setController(Controller controller) {
        this.controller = controller;
        return this;
    }


    public void register() {
        octarine.addActionHandler(toolClass, this);
    }

    public void unregister() {
        octarine.removeActionHandler(toolClass, this);
    }

    @SuppressWarnings("UnusedParameters")
    public abstract void toolActivated(Tool tool);

    @SuppressWarnings("UnusedParameters")
    public abstract void toolDeactivated(Tool tool);
}
