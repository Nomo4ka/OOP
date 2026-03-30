import LindedList.LinkedListTabulatedFunction;
import packages2.*;

public class Main {
    public static void main(String[] args) {
        double values[] = new double[] { 0, 1, 4, 9, 16, 25 };
        FunctionPoint[] egrek = new FunctionPoint[6];
        for (int i = 0; i < egrek.length; i++) {
            egrek[i] = new FunctionPoint(values[i],values[i] * values[i]);
        }
        LinkedListTabulatedFunction lTabf = new LinkedListTabulatedFunction();
        
        for (int i = 0; i < values.length; i++) {
            lTabf.addNodeByIndex(i,egrek[i]);
        }
        System.out.println(lTabf);
    }
}
