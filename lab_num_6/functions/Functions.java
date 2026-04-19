package functions;

import functions.meta.*;

public class Functions {

    private Functions() {
    }

    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY) {
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power) {
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2) {
        return new Mult(f1, f2);
    }

    public static Function composition(Function f1, Function f2) {
        return new Composition(f1, f2);
    }

    public static double Integral(Function f, double a, double b, double h) throws Exception {
        if (a < f.getLeftDomainBorder() || b > f.getRightDomainBorder()) {
            throw new Exception("Область интегрирования выходит за пределы определения функции!");
        }
        
        double Sum = 0.0;
        double x = a;
        while (x + h < b) {
            double x2 = x + h;
            Sum += (f.getFunctionValue(x) + f.getFunctionValue(x2)) * h / 2.0;
            x = x2;
        }
        
        if (x < b) {
            Sum += (f.getFunctionValue(x) + f.getFunctionValue(b)) * (b - x) / 2.0;
        }

        return Sum;
    }
}