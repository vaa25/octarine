package info.dejv.octarine.controller;

import info.dejv.octarine.model.ContainerModelElement;
import java.util.List;
import java.util.function.Predicate;

/**
 * A special type of controller, that can contain children controllers
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ContainerController
        extends Controller {

    /**
     * @return The model element associated with current ContainerController
     */
    @Override
    ContainerModelElement getModel();


    /**
     * @return The list of children controllers. This function should always return copy of the actual list, to avoid unwanted modifications
     */
    List<Controller> getChildren();


    /**
     * Lookup the controller, that matches the given predicate
     * @param predicate Test predicate
     * @return First encountered controller that matches the given predicate,
     * or null if the predicate doesn't apply to either this controller, or to any controller within its children hierarchy
     */
    Controller lookup(Predicate<Controller> predicate);
}
