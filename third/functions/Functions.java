package functions;

import functions.meta.*;

public final class Functions {
    private Functions() {
    }

    public static functions.Function shift(functions.Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }
    
}
