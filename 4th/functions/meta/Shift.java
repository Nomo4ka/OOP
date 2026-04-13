package functions.meta;

public class Shift implements functions.Function {
    private functions.Function f;
    private double shiftX;
    private double leftDomainBorder;
    private double rightDomainBorder;  
    private double shiftY;

    public Shift(functions.Function f, double shiftX , double shiftY) {
        this.f = f;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        this.leftDomainBorder = f.getLeftDomainBorder() + shiftX;
        this.rightDomainBorder = f.getRightDomainBorder() + shiftX;  
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
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }
        return f.getFunctionValue(x - shiftX) + shiftY;
    }   
}
