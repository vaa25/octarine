package info.dejv.octarine.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import static java.util.Objects.requireNonNull;

/**
 *
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class AbstractModelElement
        implements ModelElement {

    protected final Map<String, Object> chunks = new HashMap<>();

    @Override
    public boolean containsChunk(String chunkId, Class<?> chunkType) {
        requireNonNull(chunkId, "chunkId is null");
        requireNonNull(chunkType, "chunkType is null");

        return chunks.containsKey(chunkId) && chunkType.isInstance(chunks.get(chunkId));
    }


    @Override
    public <T> T getChunk(String chunkId, Class<T> chunkType) {
        requireNonNull(chunkId, "chunkId is null");
        requireNonNull(chunkType, "chunkType is null");

        if (!containsChunk(chunkId, chunkType)) {
            throw new NoSuchElementException("Requested chunk with ID[" + chunkId + "] and TYPE[" + chunkType.getName() + "] is not available");
        }

        return chunkType.cast(chunks.get(chunkId));
    }
}
