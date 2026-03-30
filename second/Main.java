import functions.*;

public class Main {
    static public void main(String[] args) {
        double[] values = {25, 16, 9, 4, 1, 0, 1, 4, 9, 16, 25};     // Значения ординат. Описывают параболу
        // Запуск проверки класса ArrayTabulatedFunction
        ArrayTabulatedFunction arrayTabFunc = new ArrayTabulatedFunction(-5, 5, values);
        System.out.println("ПРОВЕРКА ArrayTabulatedFunction:");
        TestTabulatedFunction(arrayTabFunc);    // Передаём объект ArrayTabulatedFunction

        LinkedListTabulatedFunction linkedListTabFunc = new LinkedListTabulatedFunction(-5, 5, values);
        System.out.println("\n\nПРОВЕРКА LinkedListTabulatedFunction:");
        TestTabulatedFunction(linkedListTabFunc);   // Передаём объект LinkedListTabulatedFunction
    }

    // Благодаря интерфейсу TabulatedFunction мы можем передать любой класс, его реализующий
    static void TestTabulatedFunction(TabulatedFunction function) {
        try {
            System.out.println("Функция параболы");
            System.out.println("Область определения: [" + function.getLeftDomainBorder() + "; " + function.getRightDomainBorder() + "]");
            System.out.println("Количество точек: " + function.getPointsCount());

            System.out.println("\nТочки функции:");
            System.out.println(function);

            System.out.println("\nЗначения f(x) разных точках: ");
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
            if (function.getPointsCount() > 4) {
                System.out.printf("Точка f4 = (%.3f, %.3f)%n", function.getPointX(4), function.getPointY(4));
                function.deletePoint(4);
                System.out.println(function);
            } else {
                System.out.println("Невозможно удалить: недостаточно точек");
            }

            System.out.println("\nУдаление точки с индексом 0:");
            if (function.getPointsCount() > 0) {
                System.out.printf("Точка f0 = (%.3f, %.3f)%n", function.getPointX(0), function.getPointY(0));
                function.deletePoint(0);
                System.out.println(function);
            } else {
                System.out.println("Невозможно удалить: недостаточно точек");
            }

            System.out.println("\nЗамена точки с индексом 2 на точку f = (-1.5, 2.25)");
            if (function.getPointsCount() > 2) {
                // Получаем соседние точки, чтобы определить допустимый диапазон
                double leftX = function.getPointX(1);
                double rightX = function.getPointX(3);
                double newX = (leftX + rightX) / 2; // Берем среднее значение между соседними x
                double newY = newX * newX; // Для параболы y = x^2
                
                test_point = new FunctionPoint(newX, newY);
                System.out.printf("Исходная точка: f2= (%.3f; %.3f)%n", function.getPointX(2), function.getPointY(2));
                System.out.printf("Допустимый диапазон x: (%.3f; %.3f)%n", leftX, rightX);
                function.setPoint(2, test_point);
                System.out.printf("Измененная точка: f2= (%.3f; %.3f)%n", function.getPointX(2), function.getPointY(2));
                System.out.println(function);
            } else {
                System.out.println("Невозможно заменить: недостаточно точек");
            }

            System.out.println("\nЗамена точки с индексом 0 по значению x = -4.5");
            if (function.getPointsCount() > 0) {
                System.out.printf("Исходная точка: f0= (%.3f; %.3f)%n", function.getPointX(0), function.getPointY(0));
                // Используем значение x, которое меньше следующей точки
                double nextX = function.getPointX(1);
                double newX = -4.5;
                if (newX < nextX) {
                    function.setPointX(0, newX);
                    function.setPointY(0, newX * newX);
                    System.out.printf("Измененная точка: f0= (%.3f; %.3f)%n", function.getPointX(0), function.getPointY(0));
                    System.out.println(function);
                } else {
                    System.out.printf("Невозможно установить x=%.1f, так как следующая точка имеет x=%.1f%n", newX, nextX);
                }
            } else {
                System.out.println("Невозможно заменить: недостаточно точек");
            }

            System.out.println("\nПроверка исключений:");
            
            // Проверка getPoint с неверным индексом
            try {
                System.out.println("   Проверка метода getPoint:\n" +
                        "       Попытка получить элемент с индексом " + function.getPointsCount());
                function.getPoint(function.getPointsCount());
                System.out.println("Метод успешно сработал");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка setPoint с неверным индексом
            try {
                System.out.println("\n   Проверка метода setPoint:\n" +
                        "       Попытка внести элемент с индексом " + function.getPointsCount());
                function.setPoint(function.getPointsCount(), new FunctionPoint(0, 0));
                System.out.println("Метод успешно сработал");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка setPoint с некорректной точкой (нарушение сортировки)
            try {
                System.out.println("\n   Проверка метода setPoint с некорректной точкой:");
                System.out.println("       Попытка внести точку F = (1, 1)");
                if (function.getPointsCount() > 3) {
                    function.setPoint(3, new FunctionPoint(1, 1));
                    System.out.println("    Метод успешно сработал");
                } else {
                    System.out.println("    Невозможно проверить: недостаточно точек");
                }
            } catch (InappropriateFunctionPointException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка getPointX с неверным индексом
            try {
                System.out.println("\n   Проверка метода getPointX:\n" +
                        "       Попытка вывести значение х точки с индексом " + function.getPointsCount());
                function.getPointX(function.getPointsCount());
                System.out.println("Метод успешно сработал");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка setPointX с неверным индексом
            try {
                System.out.println("\n   Проверка метода setPointX:\n" +
                        "       Попытка внести значение х элемента с индексом " + function.getPointsCount());
                function.setPointX(function.getPointsCount(), 0);
                System.out.println("Метод успешно сработал");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка setPointX с некорректным значением
            try {
                System.out.println("\n   Проверка метода setPointX с некорректным значением:");
                System.out.println("       Попытка внести значение х = 1000 ");
                if (function.getPointsCount() > 3) {
                    function.setPointX(3, 1000);
                    System.out.println("    Метод успешно сработал");
                } else {
                    System.out.println("    Невозможно проверить: недостаточно точек");
                }
            } catch (InappropriateFunctionPointException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка getPointY с неверным индексом
            try {
                System.out.println("\n   Проверка метода getPointY:\n" +
                        "       Попытка вывести значение y точки с индексом " + function.getPointsCount());
                function.getPointY(function.getPointsCount());
                System.out.println("Метод успешно сработал");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка setPointY с неверным индексом
            try {
                System.out.println("\n   Проверка метода setPointY:\n" +
                        "       Попытка внести значение Y элемента с индексом " + function.getPointsCount());
                function.setPointY(function.getPointsCount(), 0);
                System.out.println("Метод успешно сработал");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка deletePoint с неверным индексом
            try {
                System.out.println("\n   Проверка метода deletePoint:\n" +
                        "       Попытка удалить точку с индексом " + function.getPointsCount());
                function.deletePoint(function.getPointsCount());
                System.out.println("Метод успешно сработал");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }

            // Проверка addPoint с дублирующейся точкой
            try {
                System.out.println("\n   Проверка метода addPoint:\n");
                if (function.getPointsCount() > 1) {
                    System.out.printf("       Попытка добавить точку F (%.3f, %.3f):%n", 
                        function.getPointX(1), function.getPointY(1));
                    function.addPoint(new FunctionPoint(function.getPointX(1), function.getPointY(1)));
                    System.out.println("Метод успешно сработал");
                } else {
                    System.out.println("    Невозможно проверить: недостаточно точек");
                }
            } catch (InappropriateFunctionPointException e) {
                System.out.println("       Метод выдал ошибку: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}