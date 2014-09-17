package app.dejv.impl.octarine.model.chunk;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class StrokeChunk {

    private PaintChunk paint;


    public StrokeChunk(PaintChunk paint) {
        this.paint = paint;
    }


    public PaintChunk getPaint() {
        return paint;
    }
}
