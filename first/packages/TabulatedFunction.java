package packages;

import java.util.ArrayList;

public class TabulatedFunction {
    
    private FunctionPoint points[];
    private int pointsCount_;
    private double leftX_, rightX_;

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        leftX_ = leftX;
        rightX_ = rightX;
        pointsCount_ = pointsCount;
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
        if (x > leftX_ && x < rightX_) {
            for (int i = 0; i < points.size() - 1; i++) {
                FunctionPoint p1 = points.get(i);
                FunctionPoint p2 = points.get(i + 1);
                if (x > p1.y && x < p2.x) {
                    double t = (x - p1.x) / (p2.x - p1.x);
                    return p1.y + t * (p2.y - p1.x);
                }
            }
        }
        return Double.NaN;
    }

    public void deletePoint(int index) {
        points.remove(index);
    }

    public void addPoint(FunctionPoint point) {
        int inpos = 0;
        while (inpos < points.size() && points.get(inpos).x < point.x) {
            inpos++;
        }
        points.add(inpos, point);
        if (point.x < leftX_)
            leftX_ = point.x;
        if (point.x > rightX_)
            rightX_ = point.x;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tabulated Func:[");
        for (int i = 0; i < points.size(); i++) {
            sb.append(points.get(i).toString());
            if (i < points.size() - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public int getPointsCount() {
        return points.size();
    }

    public FunctionPoint getPoint(int index) {
        FunctionPoint p = new FunctionPoint(points.get(index));
        return p;
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index >= points.size()) {
            throw new IndexOutOfBoundsException("Индекс вне допустимого диапазона");
        }
        FunctionPoint newp = new FunctionPoint(point);
        points.set(index, newp);

        if (index == 0) {
            leftX_ = points.get(0).x;
        }

        if (index == points.size() - 1) {
            rightX_ = points.get(points.size() - 1).x;
        }
    }

    public double getPointX(int index) {
        return points.get(index).x;
    }

    public void setPointX(int index, double x) {
        points.get(index).x = x;

        if (index == 0) {
            leftX_ = points.get(0).x;
        }
        if (index == points.size() - 1) {
            rightX_ = points.get(points.size() - 1).x;
        }
    }

    public double getPointY(int index) {
        return points.get(index).y;
    }

    public void setPointY(int index, double y) {
        points.get(index).y = y;
    }
}
