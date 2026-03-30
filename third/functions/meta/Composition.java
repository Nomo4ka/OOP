package functions.meta;

public class Composition implements functions.Function {
    private functions.Function f;
    private functions.Function g;

    public Composition(functions.Function f, functions.Function g) {
        this.f = f;
        this.g = g;
    }

    @Override
    public double getLeftDomainBorder() {
        return Math.max(f.getLeftDomainBorder(), g.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.min(f.getRightDomainBorder(), g.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }
        return f.getFunctionValue(g.getFunctionValue(x));
    }
}
