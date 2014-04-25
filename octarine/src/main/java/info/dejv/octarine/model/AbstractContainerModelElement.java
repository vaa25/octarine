package info.dejv.octarine.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class AbstractContainerModelElement
        extends AbstractModelElement
        implements ContainerModelElement {

    private final ListProperty<ModelElement> children = new SimpleListProperty<>(FXCollections.observableArrayList());

    @Override
    public ListProperty<ModelElement> getChildren() {
        return children;
    }

}
