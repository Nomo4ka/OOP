package threads;

public class SimpleIntegrator implements Runnable {
    private Task ts;

    public SimpleIntegrator(Task ts) {
        this.ts = ts;
    }

    @Override
    public void run() {
        for(int i = 0; i < ts.getTaskCount(); i++) {
            try {
                double result = functions.Functions.Integral(ts.getFunction(), ts.getLeftX(), ts.getRightX(), ts.getStep());
                System.out.println("Результат задачи " + (i + 1) + ": " + result);
            } catch (Exception e) {
                System.out.println("Ошибка в задаче " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

}
