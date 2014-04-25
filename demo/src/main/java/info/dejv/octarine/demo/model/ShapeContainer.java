package info.dejv.octarine.demo.model;

import info.dejv.octarine.model.AbstractContainerModelElement;
import info.dejv.octarine.model.chunk.Dimension2D;

public class ShapeContainer
        extends AbstractContainerModelElement {


    public ShapeContainer() {
        chunks.put("Size", new Dimension2D(700, 700));
    }

}
