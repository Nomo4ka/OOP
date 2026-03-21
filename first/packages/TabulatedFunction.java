package packages;

public class TabulatedFunction {
    
    private FunctionPoint points[];
    private int pointsCount_;
    private double leftX_, rightX_;
    private int capacity;

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        leftX_ = leftX;
        rightX_ = rightX;
        pointsCount_ = pointsCount;
        capacity = (pointsCount_ * 3)/2;
        points = new FunctionPoint[capacity];

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
        capacity = (pointsCount_ * 3)/2;
        points = new FunctionPoint[capacity];

        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < pointsCount_; i++) {
            FunctionPoint p = new FunctionPoint(leftX + i * step, values[i]);
            points[i] = p;
        }
    }

    public double getleftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {
        return points[pointsCount_ - 1].getX();
    }

    public double getFunctionalvalue(double x) {
        if (x >= leftX_ && x <= rightX_) {
            for (int i = 0; i < pointsCount_ - 1; i++) {
                FunctionPoint p1 = points[i];
                FunctionPoint p2 = points[i + 1];
                if (x >= p1.getX() && x <= p2.getX()) {
                    double t = (x - p1.getX()) / (p2.getX() - p1.getX());
                    return p1.getY() + t * (p2.getY() - p1.getY());
                }
            }
        }
        return Double.NaN;
    }

    public int getPointsCount() {
        return pointsCount_;
    }

    public FunctionPoint getPoint(int index) {
        FunctionPoint p = new FunctionPoint(points[index]);
        return p;
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index >= pointsCount_) {
            throw new IndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (index > 0 && point.getX() <= points[pointsCount_ - 1].getX()) {
            throw new IllegalArgumentException("Точка должна быть больше предыдущей!");
        }

        if (index < pointsCount_ - 1 && point.getX() > points[index + 1].getX()) {
            throw new IllegalArgumentException("Точка должна быть меньше предыдущей!");
        }

        FunctionPoint newp = new FunctionPoint(point);
        points[index] = newp;

        if (index == 0) {
            leftX_ = points[0].getX();
        }

        if (index == points.length - 1) {
            rightX_ = points[pointsCount_ - 1].getX();
        }
    }
    
    public double getPointX(int index) {
        FunctionPoint p = points[index];
        return p.getX();
    }

    public void setPointX(int index, double x) {
        if (index < 0 || index >= pointsCount_) {
            throw new IndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (index > 0 && x <= points[index - 1].getX()) {
            throw new IllegalArgumentException("x должен быть больше предыдущей точки!");
        }

        if (index < pointsCount_ - 1 && x >= points[index + 1].getX()) {
            throw new IllegalArgumentException("x должен быть меньше следующей точки!");
        }

        FunctionPoint newp = new FunctionPoint(x, points[index].getY());
        points[index] = newp;
        points[index].setX(x);

        if (index == 0) {
            leftX_ = points[0].getX();
        }
        if (index == pointsCount_ - 1) {
            rightX_ = points[pointsCount_ - 1].getX();
        }
    }

    public double getPointY(int index) {
        FunctionPoint p = new FunctionPoint(points[index]);
        return p.getY();
    }
    
    public void setPointY(int index, double y) {
        FunctionPoint newp = new FunctionPoint(points[index].getY(), y);
        points[index] = newp;
    }

    public void deletePoint(int index) {
        FunctionPoint[] newarr = new FunctionPoint[points.length - 1];

        System.arraycopy(points, 0, newarr, 0, index);
        System.arraycopy(points, index + 1, newarr, index, pointsCount_ - index - 1);

        points = newarr;
        pointsCount_--;

        if (pointsCount_ > 0) {
            leftX_ = points[0].getX();
            rightX_ = points[pointsCount_-1].getX();
        }
    }

    //arr = {{1,2},{2,3},{2.5,1},{3,4}}
    public void addPoint(FunctionPoint point) {
        int index = rangeCheckForAdd(point.getX());
        if (pointsCount_ == points.length)
            points = grow(); 

        System.arraycopy(points, index, points, index + 1, pointsCount_ - index);

        if (index < pointsCount_ && points[index].getX() == point.getX()) {
            points[index] = point; 
            return; 
        }
        points[index] = point;
        ++pointsCount_;
        // pointsCount_ = s + 1;
    }
    
    private int rangeCheckForAdd(double x) {
        for (int i = 0; i < pointsCount_; i++) {
            if (x <= points[i].getX()) {
                return i;
            }
        }
        return pointsCount_;
    }

    private FunctionPoint[] grow() {
        int newcap = (capacity * 3) / 2;
        FunctionPoint[] newpPoints = new FunctionPoint[newcap];
        System.arraycopy(points, 0, newpPoints, 0, pointsCount_);
        return newpPoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tabulated Func:[");
        for (int i = 0; i < pointsCount_; i++) {
            sb.append(points[i].toString());
            if (i < pointsCount_ - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}