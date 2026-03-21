import packages.FunctionPoint;

public class LinkedListTabulatedFunction {
    static private class FunctionNode {
        FunctionPoint p;
        private FunctionNode next;
        private FunctionNode prev;

        FunctionNode(FunctionNode prev, FunctionPoint p, FunctionNode next) {
            this.p = p;
            this.next = next;
            this.prev = prev;
        }
    }
    
    int size = 0;
    FunctionNode first;
    FunctionNode last;

    public FunctionNode getNodeByIndex(int index) {
        if (index < size / 2) {
            FunctionNode p = first;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            return p;
        } else {
            FunctionNode p = last;
            for (int i = 0; i < size - 1; i++) {
                p = p.prev;
            }
            return p;
        }
    }

    public FunctionNode addNodeToTail(FunctionPoint p) {
        FunctionNode newNode = new FunctionNode(last, p, null);

        last.next = newNode;
        last = newNode;

        size++;

        return newNode;
    }

    public FunctionNode addNodeToHead(FunctionPoint p) {
        FunctionNode newNode = new FunctionNode(null, p, first);

        first.prev = newNode;
        first = newNode;

        ++size;

        return newNode;
    }

    FunctionNode addNodeByIndex(int index, FunctionPoint p) {
        FunctionNode newNode = new FunctionNode();

        last.next = newNode;
        last = newNode;

        size++;

        return newNode;
    }
    
}
