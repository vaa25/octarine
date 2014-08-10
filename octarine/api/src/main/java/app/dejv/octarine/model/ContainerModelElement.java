package app.dejv.octarine.model;

import javafx.beans.property.ListProperty;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ContainerModelElement
        extends ModelElement {

    ListProperty<ModelElement> getChildren();

}
