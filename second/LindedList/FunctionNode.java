package LindedList;
import packages2.*;

public class FunctionNode {
    FunctionPoint p;
    public FunctionNode next;
    public FunctionNode prev;

    FunctionNode(FunctionNode prev, FunctionPoint p, FunctionNode next) {
        this.p = p;
        this.next = next;
        this.prev = prev;
    }
}
