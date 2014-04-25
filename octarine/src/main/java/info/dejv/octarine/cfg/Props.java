package info.dejv.octarine.cfg;

/**
 *
 * @author dejv
 */
public interface Props {

    Object get(String key);

    void set(String key, Object value);
}
