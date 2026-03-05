package packages;

public class TabulatedFunction {
    
    private FunctionPoint points[];
    private int pointsCount_;
    private double leftX_, rightX_;

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        leftX_ = leftX;
        rightX_ = rightX;
        pointsCount_ = pointsCount;
        points = new FunctionPoint[pointsCount_];

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount_; i++) {
            FunctionPoint p = new FunctionPoint(leftX + i * step, 0);
            points[i] = p;
        } 
    }

    public TabulatedFunction(double leftX, double rightX, double[] values) {
        leftX_ = leftX;
        rightX_ = rightX;
        pointsCount_ = values.length;
        points = new FunctionPoint[pointsCount_];

        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < pointsCount_; i++) {
            FunctionPoint p = new FunctionPoint(leftX + i * step, values[i]);
            points[i] = p;
        }
        
    }

    public double getleftDomainBorder() {
        return points[0].x;
    }

    public double getRightDomainBorder() {
        return points[points.length - 1].x;
    }

    public double getFunctionalvalue(double x) {
        if (x >= leftX_ && x <= rightX_) {
            for (int i = 0; i < points.length - 1; i++) {
                FunctionPoint p1 = points[i];
                FunctionPoint p2 = points[i + 1];
                if (x >= p1.x && x <= p2.x) {
                    double t = (x - p1.x) / (p2.x - p1.x);
                    return p1.y + t * (p2.x - p1.x);
                }
            }
        }
        return Double.NaN;
    }

    public int getPointsCount() {
        return points.length;
    }

    public FunctionPoint getPoint(int index) {
        FunctionPoint p = new FunctionPoint(points[index]);
        return p;
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index >= points.length) {
            throw new IndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (index > 0 && point.x <= points[index - 1].x) {
            throw new IllegalArgumentException("Точка должна быть больше предыдущей!");
        }

        if (index < points.length - 1 && point.x > points[index + 1].x) {
            throw new IllegalArgumentException("Точка должна быть меньше предыдущей!");
        }

        FunctionPoint newp = new FunctionPoint(point);
        points[index] = newp;

        if (index == 0) {
            leftX_ = points[0].x;
        }

        if (index == points.length - 1) {
            rightX_ = points[points.length - 1].x;
        }
    }
    
    public double getPointX(int index) {
        FunctionPoint p = points[index];
        return p.x;
    }

    public void setPointX(int index, double x) {
        if (index < 0 || index >= points.length) {
            throw new IndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (index > 0 && x <= points[index - 1].x) {
            throw new IllegalArgumentException("x должен быть больше предыдущей точки!");
        }

        if (index < points.length - 1 && x >= points[index + 1].x) {
            throw new IllegalArgumentException("x должен быть меньше следующей точки!");
        }

        FunctionPoint newp = new FunctionPoint(x, points[index].y);
        points[index] = newp;
        points[index].x = x;

        if (index == 0) {
            leftX_ = points[0].x;
        }
        if (index == points.length - 1) {
            rightX_ = points[points.length - 1].x;
        }
    }

    public double getPointY(int index) {
        FunctionPoint p = new FunctionPoint(points[index]);
        return p.y;
    }
    
    public void setPointY(int index, double y) {
        FunctionPoint newp = new FunctionPoint(points[index].x, y);
        points[index] = newp;
    }

    public void deletePoint(int index) {
        FunctionPoint[] newarr = new FunctionPoint[points.length - 1];

        System.arraycopy(points, 0, newarr, 0, index);
        System.arraycopy(points, index + 1, newarr, index, points.length - 1 - index);

        points = newarr;

        leftX_ = points[0].x;
        rightX_ = points[points.length - 1].x;
    }
    
    public void addPoint(FunctionPoint point) {
        
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tabulated Func:[");
        for (int i = 0; i < points.length; i++) {
            sb.append(points[i].toString());
            if (i < points.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
