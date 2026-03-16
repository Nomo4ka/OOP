package packages;

public class FunctionPoint {
    protected double x, y;

    public FunctionPoint(double _x, double _y) {
        x = _x;
        y = _y;
    }

    public FunctionPoint(FunctionPoint point) {
        x = point.x;
        y = point.y;
    }

    FunctionPoint() {
        x = 0;
        y = 0;
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }
}