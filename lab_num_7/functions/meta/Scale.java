package functions.meta;

import functions.Function;

public class Scale implements Function {

    private Function f;
    private double scaleX;
    private double scaleY;
    private double leftDomainBorder;
    private double rightDomainBorder;

    public Scale(Function f, double scaleX, double scaleY) {
        this.f = f;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        double left = f.getLeftDomainBorder() * scaleX;
        double right = f.getRightDomainBorder() * scaleX;
        this.leftDomainBorder = Math.min(left, right);
        this.rightDomainBorder = Math.max(left, right);
    }

    @Override
    public double getLeftDomainBorder() {
        return leftDomainBorder;
    }

    @Override
    public double getRightDomainBorder() {
        return rightDomainBorder;
    }

    @Override
    public double getFunctionValue(double x) {
        if (x < leftDomainBorder || x > rightDomainBorder) {
            return Double.NaN;
        }

        return scaleY * f.getFunctionValue(x / scaleX);
    }
}