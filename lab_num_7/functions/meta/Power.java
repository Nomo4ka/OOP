package functions.meta;

public class Power implements functions.Function {
    private functions.Function f;
    private double n;
    private double leftDomainBorder;
    private double rightDomainBorder;  

    public Power(functions.Function f, double n) {
        this.f = f;
        this.n = n;
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
        return Math.pow(f.getFunctionValue(x), n);
    }

}
