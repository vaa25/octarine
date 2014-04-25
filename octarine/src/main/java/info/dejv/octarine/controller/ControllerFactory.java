package info.dejv.octarine.controller;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */

public interface ControllerFactory {


    Controller createController(Object model, ContainerController parent);

}
