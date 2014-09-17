package app.dejv.impl.octarine.model.chunk;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class PaintChunk {

    private ObjectProperty<Paint> paint = new SimpleObjectProperty<>(Color.BLACK);


    public Paint getPaint() {
        return paint.get();
    }


    public ObjectProperty<Paint> paintProperty() {
        return paint;
    }


    public PaintChunk setPaint(Paint paint) {
        this.paint.set(paint);
        return this;
    }

}
