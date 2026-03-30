package functions.meta;

public class Shift implements functions.Function {
    private functions.Function f;
    private double a;
    private double leftDomainBorder;
    private double rightDomainBorder;  

    public Shift(functions.Function f, double a) {
        this.f = f;
        this.a = a;
        this.leftDomainBorder = f.getLeftDomainBorder() + a;
        this.rightDomainBorder = f.getRightDomainBorder() + a;  
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
        return f.getFunctionValue(x - a);
    }   
}
