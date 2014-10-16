package app.dejv.impl.octarine.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import app.dejv.octarine.model.ModelElement;

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
    public <T> Optional<T> getChunk(String chunkId, Class<T> chunkType) {
        requireNonNull(chunkId, "chunkId is null");
        requireNonNull(chunkType, "chunkType is null");

        if (!containsChunk(chunkId, chunkType)) {
            return Optional.empty();
        }

        return Optional.of(chunkType.cast(chunks.get(chunkId)));
    }
}
