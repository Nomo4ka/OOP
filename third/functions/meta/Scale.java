package functions.meta;

public class Scale implements functions.Function {
    private functions.Function f;
    private double k;
    private double leftDomainBorder;
    private double rightDomainBorder;  

    public Scale(functions.Function f, double k) {
        this.f = f;
        this.k = k;
        this.leftDomainBorder = f.getLeftDomainBorder();
        this.rightDomainBorder = f.getRightDomainBorder();  
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
        return k * f.getFunctionValue(x);
    }
}
