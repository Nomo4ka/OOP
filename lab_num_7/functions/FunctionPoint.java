package functions;
import java.io.Serializable;

public class FunctionPoint implements Serializable, Cloneable {
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        FunctionPoint point = (FunctionPoint) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(x);
        long yBits = Double.doubleToLongBits(y);
        int xHash = (int) (xBits ^ (xBits >>> 32));
        int yHash = (int) (yBits ^ (yBits >>> 32));
        return xHash ^ yHash;
    }

    @Override
    public FunctionPoint clone() {
        return new FunctionPoint(this);
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }
}