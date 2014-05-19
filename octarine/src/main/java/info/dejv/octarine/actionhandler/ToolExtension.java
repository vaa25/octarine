package info.dejv.octarine.actionhandler;

import org.springframework.beans.factory.annotation.Autowired;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.tool.Tool;

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


    public info.dejv.octarine.actionhandler.selection.SingleSelectionToolExtension setController(Controller controller) {
        this.controller = controller;
        return null;
    }


    public void register() {
        octarine.addActionHandler(toolClass, this);
    }

    public void unregister() {
        octarine.removeActionHandler(toolClass, this);
    }

    public abstract void toolActivated(Tool tool);

    public abstract void toolDeactivated(Tool tool);
}
