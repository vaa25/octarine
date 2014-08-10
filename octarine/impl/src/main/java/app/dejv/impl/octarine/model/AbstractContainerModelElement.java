package app.dejv.impl.octarine.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import app.dejv.octarine.model.ContainerModelElement;
import app.dejv.octarine.model.ModelElement;

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
