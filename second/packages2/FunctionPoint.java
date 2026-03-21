package packages2;

public class FunctionPoint {
    private double x, y;

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
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