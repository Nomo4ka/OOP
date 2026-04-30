import functions.*;

public class Main {
    public static void main(String[] args) {
        double[] values = { 0.0, 1.0, 4.0, 9.0, 16.0, 25.0, 36.9 };
        TabulatedFunction function = new ArrayTabulatedFunction(values, 0.0, 6.0);
    }
}