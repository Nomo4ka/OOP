package functions.meta;

public class Mult implements functions.Function {
    private functions.Function f1;
    private functions.Function f2;
    private double leftDomainBorder;
    private double rightDomainBorder;  

    public Mult(functions.Function f1, functions.Function f2) {
        this.f1 = f1;
        this.f2 = f2;
        this.leftDomainBorder = Math.max(f1.getLeftDomainBorder(), f2.getLeftDomainBorder());
        this.rightDomainBorder = Math.min(f1.getRightDomainBorder(), f2.getRightDomainBorder());  
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
        return f1.getFunctionValue(x) * f2.getFunctionValue(x);
    }

}
