package functions.basic;

public class Log implements functions.Function {
    private double base;

    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        if (x <= 0) {
            return Double.NaN;
        }
        return Math.log(x) / Math.log(base);
    }

    public Log(double base) {
        this.base = base;
    }
}
