import functions.*;
import functions.basic.Exp;
import functions.basic.Log;
import threads.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("ЗАДАНИЕ 1: ПРОВЕРКА МЕТОДА ИНТЕГРИРОВАНИЯ");
        testIntegration();

        System.out.println("\nЗАДАНИЕ 2: ПОСЛЕДОВАТЕЛЬНОЕ ВЫПОЛНЕНИЕ");
        nonThread();

        System.out.println("\nЗАДАНИЕ 3: ПРОСТЫЕ ПОТОКИ");
        simpleThreads();

        System.out.println("\nЗАДАНИЕ 4: ПОТОКИ THREAD + SEMAPHOR");
        complicatedThreads();
    }

    private static void testIntegration() {
        Exp exp = new Exp();

        double leftBorder = 0.0;
        double rightBorder = 1.0;
        double theoreticalValue = Math.E - 1.0;
        double step = 0.1;

        System.out.println("Интегрирование exp(x) на [0, 1]");
        System.out.printf("Теоретическое значение: %.9f%n", theoreticalValue);

        try {
            while (true) {
                double calculatedValue = Functions.Integral(exp, leftBorder, rightBorder, step);
                double error = Math.abs(calculatedValue - theoreticalValue);

                System.out.printf(
                        "Шаг %.8f: вычислено=%.9f, ошибка=%.2e%n",
                        step, calculatedValue, error
                );

                if (error < 1e-7) {
                    System.out.println("Точность 1e-7 достигнута при шаге: " + step);
                    break;
                }

                step /= 2.0;
            }
        } catch (Exception e) {
            System.out.println("Ошибка интегрирования: " + e.getMessage());
        }
    }

    private static void nonThread() {
        Task task = new Task(100);
        Random random = new Random();

        System.out.println("Последовательное выполнение " + task.getTaskCount() + " интегралов:");
        System.out.println("Формат: leftX rightX step result");

        for (int i = 0; i < task.getTaskCount(); i++) {
            double base = 1.1 + random.nextDouble() * 8.9;

            task.setFunction(new Log(base));
            task.setLeftX(1 + random.nextDouble() * 99);
            task.setRightX(100 + random.nextDouble() * 100);
            task.setStep(0.001 + random.nextDouble() * 0.999);

            try {
                double result = Functions.Integral(
                        task.getFunction(),
                        task.getLeftX(),
                        task.getRightX(),
                        task.getStep()
                );

                System.out.printf(
                        "Результат: %.4f %.4f %.4f %.6f%n",
                        task.getLeftX(),
                        task.getRightX(),
                        task.getStep(),
                        result
                );
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void simpleThreads() {
        Task task = new Task(100);

        Thread generatorThread = new Thread(new SimpleGenerator(task));
        Thread integratorThread = new Thread(new SimpleIntegrator(task));

        generatorThread.setPriority(Thread.NORM_PRIORITY);
        integratorThread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("Запуск простых потоков");

        generatorThread.start();
        integratorThread.start();

        try {
            generatorThread.join();
            integratorThread.join();
            System.out.println("Простые потоки завершили работу");
        } catch (InterruptedException e) {
            System.out.println("Ошибка ожидания потоков: " + e.getMessage());
        }
    }

    private static void complicatedThreads() {
        Task task = new Task(100);
        Semaphor semaphor = new Semaphor();

        Generator generator = new Generator(task, semaphor);
        Integrator integrator = new Integrator(task, semaphor);

        System.out.println("Запуск потоков Generator и Integrator");

        generator.start();
        integrator.start();

        try {
            generator.join();
            integrator.join();
            System.out.println("Потоки Generator и Integrator завершили работу");
        } catch (InterruptedException e) {
            System.out.println("Ошибка ожидания потоков: " + e.getMessage());
        }
    }
}