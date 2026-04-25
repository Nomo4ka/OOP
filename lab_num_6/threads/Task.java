package threads;
import functions.*;

public class Task {
    private Function f;
    private double a;
    private double b;
    private double h;
    private int taskCount;

    public Task(Function f, double a, double b, double h, int taskCount) {
        this.f = f;
        this.a = a;
        this.b = b;
        this.h = h;
        this.taskCount = taskCount;
    }

    public Task(int taskCount) {
        this.taskCount = taskCount;
    }
    
    public Function getFunction() {
        return f;
    }

    public double getLeftX() {
        return a;
    }

    public double getRightX() {
        return b;
    }

    public double getStep() {
        return h;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setLeftX(double a) {
        this.a = a;
    }

    public void setRightX(double b) {
        this.b = b;
    }

    public void setStep(double h) {
        this.h = h;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public void setFunction(Function f) {
        this.f = f;
    }
}
