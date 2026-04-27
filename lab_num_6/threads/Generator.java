package threads;

import functions.basic.*;

public class Generator extends Thread {
    private Task ts;
    private Semaphor semaphor;

    public Generator(Task ts, Semaphor semaphor) {
        this.ts = ts;
        this.semaphor = semaphor;
    }

    @Override
    public void run() {
        for (int i = 0; i < ts.getTaskCount(); i++) {
            double base = 1 + Math.random() * 9;
            double leftX = Math.random() * 100;
            double rightX = 100 + Math.random() * 100;
            double step = 0.001 + Math.random() * 0.9;
            
            semaphor.beginWrite();
            
            ts.setFunction(new Log(base));
            ts.setLeftX(leftX);
            ts.setRightX(rightX);
            ts.setStep(step);

            System.out.printf(
                "Сгенерирована задача %d: leftX=%.4f rightX=%.4f step=%.4f base=%.4f%n",
                i + 1, leftX, rightX, step, base
            );
            semaphor.endWrite();
        }
    }

}
