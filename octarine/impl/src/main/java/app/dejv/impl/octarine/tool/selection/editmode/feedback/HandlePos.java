package app.dejv.impl.octarine.tool.selection.editmode.feedback;

import javafx.scene.Cursor;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public enum HandlePos {

    N(Cursor.N_RESIZE),
    NE(Cursor.NE_RESIZE),
    E(Cursor.E_RESIZE),
    SE(Cursor.SE_RESIZE),
    S(Cursor.S_RESIZE),
    SW(Cursor.SW_RESIZE),
    W(Cursor.W_RESIZE),
    NW(Cursor.NW_RESIZE);

    private final Cursor cursor;


    private HandlePos(Cursor cursor) {
        this.cursor = cursor;
    }


    public Cursor getCursor() {
        return cursor;
    }
}
