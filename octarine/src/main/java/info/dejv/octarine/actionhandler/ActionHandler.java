package info.dejv.octarine.actionhandler;

import info.dejv.octarine.Octarine;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.tool.Tool;

/**
 * @author dejv
 * @param <T> Tool class
 */
public abstract class ActionHandler<T extends Tool> {

    private final Class<T> toolClass;
    private final Controller controller;
    private final Octarine octarine;

    /**
     *
     * @param toolClass
     * @param controller
     */
    public ActionHandler(Class<T> toolClass, Controller controller) {
        this.toolClass = toolClass;
        this.controller = controller;
        this.octarine = controller.getRoot().getOctarine();
    }

    public Class<T> getToolClass() {
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

    public abstract void toolActivated(T tool);

    public abstract void toolDeactivated(T tool);
}
