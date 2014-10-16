package app.dejv.octarine.model;

import java.util.Optional;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ModelElement {

    boolean containsChunk(String chunkId, Class<?> chunkType);

    <T> Optional<T> getChunk(String chunkId, Class<T> chunkType);
}
