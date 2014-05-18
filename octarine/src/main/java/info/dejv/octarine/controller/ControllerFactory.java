package info.dejv.octarine.controller;

import info.dejv.octarine.model.ModelElement;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */

public interface ControllerFactory {


    Controller createController(ModelElement modelElement, ContainerController parent);

}
