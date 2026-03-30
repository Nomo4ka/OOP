import LindedList.LinkedListTabulatedFunction;
import packages2.*;

public class Main {
  static public void main(String[] args) throws InappropriateFunctionPointException {
      double[] values = { 25, 16, 9, 4, 1, 0, 1, 4, 9, 16, 25 };     // Значения ординат. Описывают параболу
        
        // Запуск проверки класса ArrayTabulatedFunction
        ArrayTabulatedFunction arrayTabFunc = new ArrayTabulatedFunction(-5, 5, values);
        System.out.println("ПРОВЕРКА ArrayTabulatedFunction:");
        TestTabulatedFunction(arrayTabFunc);    // Передаём объект ArrayTabulatedFunction

        LinkedListTabulatedFunction linkedListTabFunc = new LinkedListTabulatedFunction(-5, 5, values);
        System.out.println("ПРОВЕРКА LinkedListTabulatedFunction:");
        TestTabulatedFunction(linkedListTabFunc);   // Передаём объект LinkedListTabulatedFunction
    }

    // Благодаря интерфейсу TabulatedFunction мы можем передать любой класс, его реализующий
    static void TestTabulatedFunction(TabulatedFunction function) throws InappropriateFunctionPointException {
        System.out.println("Функция параболы");
        System.out.println("Область определения: [" + function.getLeftDomainBorder() + "; " + function.getRightDomainBorder() + "]");
        System.out.println("Количество точек: " + function.getPointsCount());

        System.out.println("\nТочки функции:");
        System.out.println(function);

        System.out.println("\nЗначения f(x)  разных точках: ");
        double[] test = {-5, -4.5, 4.5, 5, 0, 9};
        for (double x : test) {
            if (Double.isNaN(function.getFunctionalValue(x))) {
                System.out.printf("f(%.3f): не определенно%n", x);
            } else {
                System.out.printf("f(%.3f) = %.3f%n", x, function.getFunctionalValue(x));
            }
        }

        System.out.println("\nДобавление точки (0.5, 0.25): ");
        FunctionPoint test_point = new FunctionPoint(0.5, 0.25);
        function.addPoint(test_point);
        System.out.println(function);

        System.out.println("\nДобавление точки (6, 36):");
        test_point = new FunctionPoint(6, 36);
        function.addPoint(test_point);
        System.out.println(function);

        System.out.println("\nУдаление точки с индексом 4:");
        System.out.printf("Точка f4 = (%.3f, %.3f)%n", function.getPointX(4), function.getPointY(4));
        function.deletePoint(4);
        System.out.println(function);

        System.out.println("\nУдаление точки с индексом 0:");
        System.out.printf("Точка f0 = (%.3f, %.3f)%n", function.getPointX(0), function.getPointY(0));
        function.deletePoint(0);
        System.out.println(function);

        // System.out.println("\nЗамена точки с индексом 2 на точку f = (-2.5, 6.25)");
        // test_point = new FunctionPoint(-2.5, 6.25);
        // System.out.printf("Исходная точка: f2= (%.3f; %.3f)%n", function.getPointX(2), function.getPointY(2));
        // function.setPoint(2, test_point);
        // System.out.printf("Измененная точка: f2= (%.3f; %.3f)%n", function.getPointX(2), function.getPointY(2));
        // System.out.println(function);

        // System.out.println("\nЗамена точки с номером 0 по значению x = -3.5");
        // System.out.printf("Исходная точка: f5= (%.3f; %.3f)%n", function.getPointX(4), function.getPointY(4));
        // function.setPointX(0, -3.5);
        // function.setPointY(0, 12.25);
        // System.out.printf("Измененная точка: f5= (%.3f; %.3f)%n", function.getPointX(4), function.getPointY(4));
        // System.out.println(function);

        System.out.println("\nПроверка исключений:");
        try{
            System.out.println("   Проверка метода getPoint:\n" +
                    "       Попытка получить элемент с индексом "+function.getPointsCount());
            function.getPoint(function.getPointsCount());
            System.out.println("Метод успешно сработал");
        }
        catch (FunctionPointIndexOutOfBoundsException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n   Проверка метода setPoint:\n" +
                    "       Попытка внести элемент с индексом "+function.getPointsCount());
            function.setPoint(function.getPointsCount(), new FunctionPoint(0,0));
            System.out.println("Метод успешно сработал");
        }
        catch (FunctionPointIndexOutOfBoundsException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n       Попытка внести точку F = (1, 1)");
            function.setPoint(3, new FunctionPoint(1,1));
            System.out.println("    Метод успешно сработал");
        }
        catch (InappropriateFunctionPointException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n   Проверка метода getPointX:\n" +
                    "       Попытка вывести значение х точки с индексом "+function.getPointsCount());
            function.getPointX(function.getPointsCount());
            System.out.println("Метод успешно сработал");
        }
        catch (FunctionPointIndexOutOfBoundsException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n   Проверка метода setPointX:\n" +
                    "       Попытка внести значение х элемента с индексом "+function.getPointsCount());
            function.setPointX(function.getPointsCount(), 0);
            System.out.println("Метод успешно сработал");
        }
        catch (FunctionPointIndexOutOfBoundsException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n       Попытка внести значение х = 1000 ");
            function.setPointX(3,1000);
            System.out.println("    Метод успешно сработал");
        }
        catch (InappropriateFunctionPointException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n   Проверка метода getPointY:\n" +
                    "       Попытка вывести значение y точки с индексом "+function.getPointsCount());
            function.getPointY(function.getPointsCount());
            System.out.println("Метод успешно сработал");
        }
        catch (FunctionPointIndexOutOfBoundsException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n   Проверка метода setPointY:\n" +
                    "       Попытка внести значение Y элемента с индексом "+function.getPointsCount());
            function.setPointY(function.getPointsCount(), 0);
            System.out.println("Метод успешно сработал");
        }
        catch (FunctionPointIndexOutOfBoundsException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n   Проверка метода deletePoint:\n" +
                    "       Попытка удалить точку с индексом "+function.getPointsCount());
            function.deletePoint(function.getPointsCount());
            System.out.println("Метод успешно сработал");
        }
        catch (FunctionPointIndexOutOfBoundsException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }

        try{
            System.out.println("\n   Проверка метода addPoint:\n");
            System.out.printf("       Попытка добавить точку F (%.3f, %.3f):%n", function.getPointX(1),function.getPointY(1));
            function.addPoint(new FunctionPoint(function.getPointX(1),function.getPointY(1)));
            System.out.println("Метод успешно сработал");
        }
        catch (InappropriateFunctionPointException e){
            System.out.println("       Метод выдал ошибку: "+ e.getMessage());
        }
    }
}
