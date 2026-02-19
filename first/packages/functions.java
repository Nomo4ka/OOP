package packages;

import java.util.ArrayList;

public class functions {
    protected static class FunctionPoint {
        private double x, y;
        
        FunctionPoint(double _x, double _y) {
            x = _x;
            y = _y;
        }

        FunctionPoint(FunctionPoint point) {
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
    
    public static class TabulatedFunction {
        ArrayList<FunctionPoint> points = new ArrayList<>();
        private double leftX_, rightX_;

        public TabulatedFunction(double leftX, double rightX, int pointsCount) {
            leftX_ = leftX;
            rightX_ = rightX;
            double step = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; i < pointsCount; i++) {
                FunctionPoint p = new FunctionPoint(leftX + i * step, 0);
                points.add(p);
            }
        }

        public TabulatedFunction(double leftX, double rightX, double[] values) {
            leftX_ = leftX;
            rightX_ = rightX;
            double step = (rightX - leftX) / (values.length - 1);
            for (int i = 0; i < values.length; i++) {
                FunctionPoint p = new FunctionPoint(leftX + i * step, values[i]);
                points.add(p);
            }
        }

        public double getleftDomainBorder() {
            return points.get(0).x;
        }

        public double getRightDomainBorder() {
            return points.get(points.size() - 1).x;
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
            if (point.x < leftX_) leftX_ = point.x;
            if(point.x > rightX_) rightX_ = point.x;
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
    }
    
}


