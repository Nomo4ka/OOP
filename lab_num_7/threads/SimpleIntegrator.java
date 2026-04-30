package threads;

import functions.*;

public class SimpleIntegrator implements Runnable {
    private Task ts;

    public SimpleIntegrator(Task ts) {
        this.ts = ts;
    }

    @Override
    public void run() {
        for (int i = 0; i < ts.getTaskCount(); i++) {
            Function f;
            double leftX;
            double rightX;
            double step;
            
            synchronized (ts) {

                f = ts.getFunction();
                leftX = ts.getLeftX();
                rightX = ts.getRightX();
                step = ts.getStep();
            }
            
            try {
                double result = Functions.Integral(f, leftX, rightX, step);
                System.out.println("Результат задачи " + (i + 1) + ": " + result);
            } catch (Exception e) {
                System.out.println("Ошибка в задаче " + (i + 1) + ": " + e.getMessage());
            }
        }
    }
}
