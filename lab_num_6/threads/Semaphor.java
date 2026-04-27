package threads;

public class Semaphor {
    public  Semaphor(){}
    private boolean isAvailable = false;

    public synchronized void beginWrite() {
        while (isAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public synchronized void endWrite() {
        isAvailable = true;
        notifyAll();
    }

    public synchronized void beginRead() {
        while (!isAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public synchronized void endRead() {
        isAvailable = false;
        notifyAll();
    }
}
