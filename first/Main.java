// import packages.*;
import java.util.Scanner;

import packages.functions.TabulatedFunction;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] values = new double[5];
        for (int i = 0; i < values.length - 1; i++) {
            System.out.print("Введите элемент[" + i + "]: ");
            values[i] = scanner.nextDouble();
        }

        TabulatedFunction tabf = new TabulatedFunction(0, 10.0, values);
        System.out.println(tabf);

        scanner.close();
    }
}

