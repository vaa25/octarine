package info.dejv.octarine.model;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public interface ModelElement {

    boolean containsChunk(String chunkId, Class<?> chunkType);

    <T> T getChunk(String chunkId, Class<T> chunkType);
}
