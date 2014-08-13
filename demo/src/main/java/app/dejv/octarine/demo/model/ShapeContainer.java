package app.dejv.octarine.demo.model;

import org.springframework.stereotype.Component;

import app.dejv.impl.octarine.model.AbstractContainerModelElement;
import app.dejv.impl.octarine.model.BasicProperties;
import app.dejv.impl.octarine.model.chunk.DoubleTuple;


@Component
public class ShapeContainer
        extends AbstractContainerModelElement {


    public ShapeContainer() {
        chunks.put(BasicProperties.SIZE, new DoubleTuple(700, 700));
    }

}
