package threads;

import functions.basic.*;

public class SimpleGenerator implements Runnable {
    private Task ts;

    public SimpleGenerator(Task ts) {
        this.ts = ts;
    }

    @Override
    public void run() {
        for (int i = 0; i < ts.getTaskCount(); i++) {
            double base = 1 + Math.random() * 9;
            double leftX = Math.random() * 100;
            double rightX = 100 + Math.random() * 100;
            double step = 0.001 + Math.random() * 0.9;

            synchronized (ts) {
                ts.setFunction(new Log(base));
                ts.setLeftX(leftX);
                ts.setRightX(rightX);
                ts.setStep(step);
            }
            
            System.out.printf(
                "Сгенерирована задача %d: leftX=%.4f rightX=%.4f step=%.4f base=%.4f%n",
                i + 1, leftX, rightX, step, base
            );
        }
    }
}
