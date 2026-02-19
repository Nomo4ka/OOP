// import packages.*;
import packages.functions.TabulatedFunction;

public class Main {
    public static void main(String[] args) {
        double[] values = {1.0, 2.0, 3.0, 4.0, 5.0};
        TabulatedFunction tabf = new TabulatedFunction(0, 10.0, values);
        System.out.println(tabf);
        System.out.println(tabf.getFunctionalvalue(3));
    }
}

