package app.dejv.impl.octarine.cfg;

/**
 *
 * @author dejv
 */
public interface Props {

    Object get(String key);

    void set(String key, Object value);
}
