package app.dejv.octarine.controller;


import app.dejv.octarine.model.ModelElement;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */

public interface ControllerFactory {


    Controller createController(ModelElement modelElement, ContainerController parent);

}
