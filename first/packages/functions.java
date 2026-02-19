package packages;

import java.util.ArrayList;

public class functions {
    public static class FunctionPoint {
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

        public TabulatedFunction(double leftX, double rightX, int pointsCount) {
            double step = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; i < pointsCount; i++) {
                FunctionPoint p = new FunctionPoint(leftX + i * step, 0);
                points.add(p);
            }
        }

        public TabulatedFunction(double leftX, double rightX, double[] values) {
            double step = (rightX - leftX) / (values.length - 1);
            for (int i = 0; i < values.length; i++) {
                FunctionPoint p = new FunctionPoint(leftX + i * step, values[i]);
                points.add(p);
            }
        }

        public double getleftDomainBorder() {
            FunctionPoint lefB = points.get(0);
            return lefB.x;
        }

        public double getRightDomainBorder() {
            FunctionPoint rightB = points.get(points.size() - 1);
            return rightB.x;
        }

        public double getFunctionalvalue(double x){
            
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


