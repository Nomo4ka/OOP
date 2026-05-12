/**
 * Основной класс для тестирования табличных функций (лабораторная работа 4).
 * Проверяет создание, табулирование, запись/чтение функций и сериализацию.
 */
import functions.*;
import java.io.*;
import functions.basic.*;

class Lab4 {

    /**
     * Точка входа в программу. Запускает все тесты заданий 8 и 9.
     */
    public static void main(String[] args) {
        try {
            System.out.println("=== ЗАДАНИЕ 8: ПРОВЕРКА РАБОТЫ НАПИСАННЫХ КЛАССОВ ===\n");

            // Часть 1: Тестирование Sin и Cos, табулирование и сравнение
            System.out.println(">>> Часть 1: Sin и Cos, табулирование и сравнение");
            testTask8Part1();

            // Часть 2: Сумма квадратов sin и cos
            System.out.println("\n>>> Часть 2: Сумма квадратов sin и cos для разных точек");
            testTask8Part2();

            // Часть 3: Экспонента в текстовом формате
            System.out.println("\n>>> Часть 3: Экспонента в текстовом формате, запись и чтение");
            TabulatedFunction textFunc = testTask8Part3();

            // Часть 4: Логарифм в бинарном формате
            System.out.println("\n>>> Часть 4: Логарифм в бинарном формате, запись и чтение");
            TabulatedFunction binaryFunc = testTask8Part4();

            System.out.println("\n=== ЗАДАНИЕ 9: СЕРИАЛИЗАЦИЯ ===\n");

            // ln(exp(x))
            Function composition = Functions.composition(new Log(Math.E), new Exp());

            // Serializable для ArrayTabulatedFunction
            System.out.println(">>> Serializable (ArrayTabulatedFunction)");
            ArrayTabulatedFunction tabSer = testTask9Serializable(composition);

            // Externalizable для специального класса
            System.out.println(">>> Externalizable (LinkedListTabulatedFunction)");
            LinkedListTabulatedFunctionExternalizable tabExt = testTask9Externalizable(composition);

            // Сравнение всех способов хранения
            System.out.println("\n=== СРАВНЕНИЕ ВСЕХ СПОСОБОВ ХРАНЕНИЯ ===");
            compareAllMethods(textFunc, binaryFunc, tabSer, tabExt);

        } catch (Exception e) {
            System.out.println("Ошибка при тестировании: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Часть 1 задания 8: Создание Sin/Cos, табулирование на [0, π] и сравнение.
     */
    public static void testTask8Part1() throws Exception {
        System.out.println("1. СОЗДАНИЕ Sin И Cos, ВЫВОД ЗНАЧЕНИЙ НА [0, pi] С ШАГОМ 0.1:");

        Sin sin = new Sin();
        Cos cos = new Cos();

        System.out.println("\nЗначения Sin и Cos:");
        System.out.println("x\t\tSin(x)\t\tCos(x)");
        for (double x = 0; x <= Math.PI; x += 0.1) {
            System.out.printf("%.1f\t\t%.6f\t%.6f%n", x, sin.getFunctionValue(x), cos.getFunctionValue(x));
        }

        System.out.println("\n2. СОЗДАНИЕ ТАБУЛИРОВАННЫХ АНАЛОГОВ С 10 ТОЧКАМИ:");

        TabulatedFunction tabulatedSin = TabulatedFunctions.tabulate(sin, 0, Math.PI, 10);
        TabulatedFunction tabulatedCos = TabulatedFunctions.tabulate(cos, 0, Math.PI, 10);

        System.out.println("\nТочки табулированного синуса:");
        printPoints(tabulatedSin);
        System.out.println("\nТочки табулированного косинуса:");
        printPoints(tabulatedCos);

        System.out.println("\n3. СРАВНЕНИЕ ИСХОДНЫХ И ТАБУЛИРОВАННЫХ ФУНКЦИЙ:");
        System.out.println("x\t\tSin(x)\t\tTabSin(x)\tРазница\t\tCos(x)\t\tTabCos(x)\tРазница");
        for (double x = 0; x <= Math.PI; x += 0.1) {
            double sinExact = sin.getFunctionValue(x);
            double sinTab = tabulatedSin.getFunctionValue(x);
            double sinDiff = Math.abs(sinExact - sinTab);

            double cosExact = cos.getFunctionValue(x);
            double cosTab = tabulatedCos.getFunctionValue(x);
            double cosDiff = Math.abs(cosExact - cosTab);

            System.out.printf("%.1f\t\t%.4f\t\t%.4f\t\t%.6f\t%.4f\t\t%.4f\t\t%.6f%n",
                    x, sinExact, sinTab, sinDiff, cosExact, cosTab, cosDiff);
        }
    }

    /**
     * Вспомогательный метод для вывода точек табличной функции.
     */
    private static void printPoints(TabulatedFunction func) {
        for (int i = 0; i < func.getPointsCount(); i++) {
            System.out.printf("Точка %d: (%.6f, %.6f)%n", i, func.getPointX(i), func.getPointY(i));
        }
    }

    /**
     * Часть 2 задания 8: Сумма квадратов sin²(x) + cos²(x) для разного числа точек.
     */
    public static void testTask8Part2() throws Exception {
        System.out.println("\n4. СУММА КВАДРАТОВ ТАБУЛИРОВАННЫХ СИНУСА И КОСИНУСА:");

        int[] pointCounts = {5, 10, 20, 50};

        for (int pointsCount : pointCounts) {
            System.out.println("\nКоличество точек в табулированных аналогах: " + pointsCount);

            TabulatedFunction tabulatedSin = TabulatedFunctions.tabulate(new Sin(), 0, Math.PI, pointsCount);
            TabulatedFunction tabulatedCos = TabulatedFunctions.tabulate(new Cos(), 0, Math.PI, pointsCount);

            Function sumOfSquares = Functions.sum(
                    Functions.power(tabulatedSin, 2),
                    Functions.power(tabulatedCos, 2)
            );

            System.out.println("x\t\tsin^2(x)+cos^2(x)");
            for (double x = 0; x <= Math.PI; x += 0.1) {
                double result = sumOfSquares.getFunctionValue(x);
                System.out.printf("%.1f\t\t%.8f%n", x, result);
            }

            double totalDeviation = 0;
            int samples = 0;
            for (double x = 0; x <= Math.PI; x += 0.05) {
                double deviation = Math.abs(1.0 - sumOfSquares.getFunctionValue(x));
                totalDeviation += deviation;
                samples++;
            }
            System.out.printf("Среднее отклонение от 1: %.10f%n", totalDeviation / samples);
        }
    }

    /**
     * Часть 3 задания 8: Запись/чтение экспоненты в текстовом формате.
     */
    public static TabulatedFunction testTask8Part3() throws Exception {
        System.out.println("\n5. ЭКСПОНЕНТА - ТЕКСТОВЫЙ ФОРМАТ:");

        TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11);

        System.out.println("\nИсходная табулированная экспонента (11 точек):");
        printPoints(tabulatedExp);

        try (FileWriter writer = new FileWriter("exp_function.txt")) {
            TabulatedFunctions.writeTabulatedFunction(tabulatedExp, writer);
            System.out.println("\nФункция записана в файл: exp_function.txt");
        }

        TabulatedFunction readExp;
        try (FileReader reader = new FileReader("exp_function.txt")) {
            readExp = TabulatedFunctions.readTabulatedFunction(reader);
            System.out.println("Функция прочитана из файла: exp_function.txt");
        }

        System.out.println("\nСРАВНЕНИЕ ИСХОДНОЙ И ПРОЧИТАННОЙ ЭКСПОНЕНТЫ:");
        compareFunctions(tabulatedExp, readExp, 0, 10, 1.0);

        return readExp;
    }

    /**
     * Часть 4 задания 8: Запись/чтение логарифма в бинарном формате.
     */
    public static TabulatedFunction testTask8Part4() throws Exception {
        System.out.println("\n6. ЛОГАРИФМ - БИНАРНЫЙ ФОРМАТ:");

        TabulatedFunction tabulatedLog = TabulatedFunctions.tabulate(new Log(Math.E), 1, 10, 11);

        System.out.println("\nИсходный табулированный логарифм (11 точек):");
        printPoints(tabulatedLog);

        try (FileOutputStream fos = new FileOutputStream("log_function.bin")) {
            TabulatedFunctions.outputTabulatedFunction(tabulatedLog, fos);
            System.out.println("\nФункция записана в файл: log_function.bin");
        }

        TabulatedFunction readLog;
        try (FileInputStream fis = new FileInputStream("log_function.bin")) {
            readLog = TabulatedFunctions.inputTabulatedFunction(fis);
            System.out.println("Функция прочитана из файла: log_function.bin");
        }

        System.out.println("\nСРАВНЕНИЕ ИСХОДНОГО И ПРОЧИТАННОГО ЛОГАРИФМА:");
        compareFunctions(tabulatedLog, readLog, 1, 10, 1.0);

        return readLog;
    }

    /**
     * Вспомогательный метод для сравнения двух функций на интервале.
     */
    private static void compareFunctions(TabulatedFunction original, TabulatedFunction read,
                                         double start, double end, double step) {
        for (double x = start; x <= end; x += step) {
            double orig = original.getFunctionValue(x);
            double r = read.getFunctionValue(x);
            boolean matches = Math.abs(orig - r) < 1e-10;
            System.out.printf("%.1f: Исходная=%.6f, Прочитанная=%.6f, Совпадают=%s%n",
                    x, orig, r, matches ? "Да" : "НЕТ!");
        }
    }

    /**
     * Задание 9: Тест Serializable для ArrayTabulatedFunction.
     */
    public static ArrayTabulatedFunction testTask9Serializable(Function sourceFunc) throws Exception {

        ArrayTabulatedFunction tabFunc =
                (ArrayTabulatedFunction) TabulatedFunctions.tabulate(sourceFunc, 0, 10, 11);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ser.bin"))) {
            out.writeObject(tabFunc);
        }

        ArrayTabulatedFunction funcRead;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("ser.bin"))) {
            funcRead = (ArrayTabulatedFunction) in.readObject();
        }

        System.out.println("Сравнение исходной и десериализованной Serializable-функции:");
        compareFunctions(tabFunc, funcRead, 0, 10, 1.0);

        System.out.println("ArrayTabulatedFunction сериализована и десериализована успешно.");
        return funcRead;
    }

    /**
     * Задание 9: Тест Externalizable для LinkedListTabulatedFunction.
     */
    public static LinkedListTabulatedFunctionExternalizable testTask9Externalizable(Function sourceFunc) throws Exception {

        FunctionPoint[] points = new FunctionPoint[11];
        for (int i = 0; i <= 10; i++) {
            double x = i;
            points[i] = new FunctionPoint(x, sourceFunc.getFunctionValue(x));
        }

        LinkedListTabulatedFunctionExternalizable tabFunc =
                new LinkedListTabulatedFunctionExternalizable(points);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ext.bin"))) {
            out.writeObject(tabFunc);
        }

        LinkedListTabulatedFunctionExternalizable funcRead;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("ext.bin"))) {
            funcRead = (LinkedListTabulatedFunctionExternalizable) in.readObject();
        }

        System.out.println("Сравнение исходной и десериализованной Externalizable-функции:");
        compareFunctions(tabFunc, funcRead, 0, 10, 1.0);

        System.out.println("LinkedListTabulatedFunction внешне сериализована и десериализована успешно.");
        return funcRead;
    }

    /**
     * Сравнение значений всех сохраненных функций и размеров файлов.
     */
    private static void compareAllMethods(TabulatedFunction textFunc, TabulatedFunction binaryFunc,
                                          TabulatedFunction tabSer, TabulatedFunction tabExt) throws Exception {
        int points = Math.min(Math.min(textFunc.getPointsCount(), binaryFunc.getPointsCount()),
                Math.min(tabSer.getPointsCount(), tabExt.getPointsCount()));

        System.out.println("x\t\tText\t\tBinary\t\tSerializable\tExternalizable");
        for (int i = 0; i < points; i++) {
            double vText = textFunc.getPointY(i);
            double vBin = binaryFunc.getPointY(i);
            double vSer = tabSer.getPointY(i);
            double vExt = tabExt.getPointY(i);

            System.out.printf("%.1f\t\t%.6f\t%.6f\t%.6f\t\t%.6f%n",
                    textFunc.getPointX(i), vText, vBin, vSer, vExt);
        }

        System.out.println("\nРазмер файлов:");
        printFileSize("exp_function.txt");
        printFileSize("log_function.bin");
        printFileSize("ser.bin");
        printFileSize("ext.bin");

        System.out.println("\n=== ВСЕ ЗАДАНИЯ УСПЕШНО ВЫПОЛНЕНЫ ===");
    }

    /**
     * Вспомогательный метод для вывода размера файла.
     */
    private static void printFileSize(String filename) {
        File file = new File(filename);
        System.out.println(file.getName() + ": " + file.length() + " байт");
    }
}