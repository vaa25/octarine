package info.dejv.octarine.demo.model;

import org.springframework.stereotype.Component;

import info.dejv.octarine.model.AbstractContainerModelElement;
import info.dejv.octarine.model.BasicProperties;
import info.dejv.octarine.model.chunk.DoubleTuple;

@Component
public class ShapeContainer
        extends AbstractContainerModelElement {


    public ShapeContainer() {
        chunks.put(BasicProperties.SIZE, new DoubleTuple(700, 700));
    }

}
