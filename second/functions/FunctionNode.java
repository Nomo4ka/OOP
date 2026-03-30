package functions;

public class FunctionNode {
    FunctionPoint p;
    protected FunctionNode next;
    protected FunctionNode prev;
    
    FunctionNode(FunctionNode prev, FunctionPoint p, FunctionNode next) {
        this.prev = prev;
        this.p = p;
        this.next = next;
    }
    
    FunctionNode(FunctionPoint p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", p.getX(), p.getY());
    }
}
