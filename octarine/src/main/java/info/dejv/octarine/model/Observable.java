package info.dejv.octarine.model;

import java.beans.PropertyChangeListener;

/**
 *
 * @author dejv
 */
public interface Observable {

    static String PROP_UPDATED = "Updated";
    static String PROP_CHILD_ADDED = "ChildAdded";
    static String PROP_CHILD_REMOVED = "ChildRemoved";


    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
