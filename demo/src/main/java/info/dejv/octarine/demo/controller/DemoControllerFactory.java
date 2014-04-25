package info.dejv.octarine.demo.controller;

import info.dejv.octarine.controller.ContainerController;
import info.dejv.octarine.controller.Controller;
import info.dejv.octarine.controller.ControllerFactory;
import info.dejv.octarine.demo.model.RectangleShape;

public class DemoControllerFactory
        implements ControllerFactory {


    @Override
    public Controller createController(Object model, ContainerController parent) {

        if (model instanceof RectangleShape) {
            return new RectangleShapeController((RectangleShape) model, parent);
        }

        return null;
    }

}
