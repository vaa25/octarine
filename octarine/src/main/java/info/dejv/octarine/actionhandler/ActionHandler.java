package info.dejv.octarine.actionhandler;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.tool.Tool;

/**
 * @author dejv
 * @param <T> Tool class
 */
public abstract class ActionHandler {

    private final Class<? extends Tool> toolClass;
    private final Controller controller;
    private final Octarine octarine;

    /**
     *
     * @param toolClass
     * @param controller
     */
    public ActionHandler(Class<? extends Tool> toolClass, Controller controller) {
        this.toolClass = toolClass;
        this.controller = controller;
        this.octarine = controller.getRoot().getOctarine();
    }

    public Class<? extends Tool> getToolClass() {
        return toolClass;
    }


    public Controller getController() {
        return controller;
    }


    public Octarine getOctarine() {
        return octarine;
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
