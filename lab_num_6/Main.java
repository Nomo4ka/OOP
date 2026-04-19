import java.util.Random;

import functions.*;
import functions.basic.Exp;
import functions.basic.Log;
import threads.*;

public class Main {
    public static void main(String[] args) {
        //testIntegral();
        //nonThread();    
        simpleThreads();
    }   

    public static void testIntegral() {
        Function exp = new Exp();
        double exact = Math.exp(1) - 1; // Точное значение интеграла от exp(x) на [0, 1]

        try {
            double h = 0.1;
            double value = Functions.Integral(exp, 0.0, 1.0, h);

            System.out.println("Теоретическое значение: " + exact);
            System.out.println("Численное значение (h = " + h + "): " + value);
            System.out.println("Погрешность: " + Math.abs(exact - value));

            double step = 0.1;
            while (true) {
                double approx = Functions.Integral(exp, 0.0, 1.0, step);
                double error = Math.abs(exact - approx);

                if (error < 1e-7) {
                    System.out.println("Шаг для точности до 7-го знака после запятой: " + step);
                    System.out.println("Приближенное значение: " + approx);
                    System.out.println("Погрешность: " + error);
                    break;
                }

                step /= 2.0;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void nonThread() {
        try {
            Task task = new Task(10); // 10 заданий
            Random random = new Random();

            System.out.println("Последовательное выполнение " + task.getTaskCount() + " интегралов:");
            System.out.println("Формат: leftX rightX step result");

            for (int i = 0; i < task.getTaskCount(); i++) {
                // Генерация случайных параметров
                double base = 1.1 + random.nextDouble() * 8.9; // основание логарифма [1.1, 10]
                task.setFunction(new Log(base));
                task.setLeftX(random.nextDouble() * 100); // левая граница [0, 100]
                task.setRightX(100 + random.nextDouble() * 100); // правая граница [100, 200]
                task.setStep(0.001 + random.nextDouble() * 0.999); // шаг [0.001, 1]

                System.out.printf("Исходные: %.4f %.4f %.4f%n",
                        task.getLeftX(), task.getRightX(), task.getStep());

                // Последовательное вычисление интеграла
                double result = Functions.Integral(task.getFunction(), task.getLeftX(), task.getRightX(),
                        task.getStep());
                System.out.printf("Результат: %.4f %.4f %.4f %.6f%n",
                        task.getLeftX(), task.getRightX(), task.getStep(), result);
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void simpleThreads() {
        Task task = new Task(100);
        
        // Создание потоков генератора и интегратора
        Thread generatorThread = new Thread(new SimpleGenerator(task));
        Thread integratorThread = new Thread(new SimpleIntegrator(task));

        // Настройка приоритетов потоков
        generatorThread.setPriority(Thread.MAX_PRIORITY);
        integratorThread.setPriority(Thread.MIN_PRIORITY);
        System.out.println("Запуск простых потоков (Generator: NORM_PRIORITY, Integrator: MAX_PRIORITY)");

        // Запуск потоков
        generatorThread.start();
        integratorThread.start();

        // Ожидание завершения потоков
        try {
            generatorThread.join();
            integratorThread.join();
            System.out.println("✅ Простые потоки завершили работу");
        } catch (InterruptedException e) {
            System.err.println("Ошибка ожидания потоков: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }
}