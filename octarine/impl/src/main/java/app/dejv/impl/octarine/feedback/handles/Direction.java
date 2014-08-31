package app.dejv.impl.octarine.feedback.handles;

import javafx.scene.Cursor;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public enum Direction {

    N(Cursor.N_RESIZE),
    NE(Cursor.NE_RESIZE),
    E(Cursor.E_RESIZE),
    SE(Cursor.SE_RESIZE),
    S(Cursor.S_RESIZE),
    SW(Cursor.SW_RESIZE),
    W(Cursor.W_RESIZE),
    NW(Cursor.NW_RESIZE);

    private final Cursor cursor;


    private Direction(Cursor cursor) {
        this.cursor = cursor;
    }


    public Cursor getCursor() {
        return cursor;
    }

    public Direction getOpposite() {
        switch (this) {
            case N: return S;
            case NE: return SW;
            case E: return W;
            case SE: return NW;
            case S : return N;
            case SW: return NE;
            case W: return E;
            case NW: return SE;
        }
        return null;
    }
}
