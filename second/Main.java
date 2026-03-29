import java.util.LinkedList;

import LindedList.LinkedListTabulatedFunction;
import packages2.*;

public class Main {
    public static void main(String[] args) {
        // double values[] = new double[] { 0, 1, 4, 9, 16, 25 };
        // TabulatedFunction tabf = new TabulatedFunction(0, 5, values);
        
        // double argxL = tabf.getleftDomainBorder();
        // double argxR = tabf.getRightDomainBorder();
        
        // System.out.println("текущая левая граница функции: " + argxL);
        // System.out.println("текущая правая граница функции: " + argxR);
        // System.out.println("Значение функции в точке: " + 0.25 + " " + tabf.getFunctionalvalue(0.25));
        // System.out.println("\n=== Функция до добавления точки ===");
        // System.out.println(tabf);
        // System.out.println("Количество точек: " + tabf.getPointsCount());
        // System.out.println("\n=== Добавление точки (2.5, 6.25) ===");
        // FunctionPoint newPoint = new FunctionPoint(2.5, 6.25);
        // try {
        //     tabf.addPoint(newPoint);
        // } catch (InappropriateFunctionPointException e) {
        //     System.err.println("Не удалось добавить точку!");
        // }

        // System.out.println("\n=== Функция после добавления точки ===");
        // System.out.println(tabf);
        // System.out.println("Количество точек: " + tabf.getPointsCount());
        // System.out.println("Левая граница: " + tabf.getleftDomainBorder());
        // System.out.println("Правая граница: " + tabf.getRightDomainBorder());
        
        // System.out.println("\n=== Проверка значений ===");
        // System.out.println("Значение в x=2.5 (должно быть 6.25): " + tabf.getFunctionalvalue(2.5));
        // System.out.println("Значение в x=2.5 через getPoint: " + tabf.getPointY(3)); 
        
        // System.out.println("\n=== Удаление точки с индексом 2 ===");
        // tabf.deletePoint(2);
        
        // System.out.println("\n=== Функция после удаления точки ===");
        // System.out.println(tabf);
        // System.out.println("Количество точек: " + tabf.getPointsCount());
        double values[] = new double[] { 0, 1, 4, 9, 16, 25 };
        FunctionPoint[] egrek = new FunctionPoint[6];
        for (int i = 0; i < egrek.length; i++) {
            egrek[i] = new FunctionPoint(values[i],values[i] * values[i]);
        }
        LinkedListTabulatedFunction lTabf = new LinkedListTabulatedFunction();
        
        for (int i = 0; i < values.length; i++) {
            lTabf.addNodeByIndex(i,egrek[i]);
        }
    }
}
