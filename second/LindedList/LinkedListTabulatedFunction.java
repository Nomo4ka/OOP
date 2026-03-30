package LindedList;

import packages2.FunctionPoint;


public class LinkedListTabulatedFunction {
   
    int size = 0;
    FunctionNode head;
    FunctionNode tail;

    public LinkedListTabulatedFunction() {
        head = null;
        tail = null;
    }

    public FunctionNode getNodeByIndex(int index) {
        if (index < size / 2) {
            FunctionNode p = head;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            return p;
        } else {
            FunctionNode p = tail;
            for (int i = size ; i > index ; --i) {
                p = p.prev;
            }
            return p;
        }
    }

    public FunctionNode addNodeToTail(FunctionPoint p) {
        FunctionNode newNode = new FunctionNode(p);
        if(head == null){
            head = tail = newNode;
        } else { 
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return newNode;
    }

    public FunctionNode addNodeToHead(FunctionPoint p) {
        FunctionNode newNode = new FunctionNode(p);
        if (head == null) {
            head = tail = newNode;
        } else { 
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        size++;
        return newNode;
    }

    public FunctionNode addNodeByIndex(int index, FunctionPoint p) {
        FunctionNode newNode;

        if (index == 0) {
            addNodeToHead(p);
            newNode = head;
        }
        else if (index == size) {
            addNodeToTail(p);
            newNode = tail;
        }
        else {
            FunctionNode node;
            if (index < size / 2) {
                node = head;
                for (int i = 0; i < index; i++) {
                    node = node.next;
                }
            } else {
                node = tail;
                for (int i = size - 1; i > index; --i) {
                    node = node.prev;
                }
            }

            newNode = new FunctionNode(p);
            FunctionNode prevNode = node.prev;

            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = node;
            node.prev = newNode;

            ++size;
        }
        return newNode;
    }
    
    public FunctionNode deleteNodeByIndex(int index) {

        FunctionNode deletedNode;

        if (size == 1) {
            deletedNode = head;
            head = null;
            tail = null;
        } 
        else if (index == 0) {
            deletedNode = head;
            head = head.next;
            head.prev = null;
        } 
        else if (index == size - 1) {
            deletedNode = tail;
            tail = tail.prev;
            tail.next = null;
        } 
        else {
            deletedNode = head;
            if (index < size / 2) {
                for (int i = 0; i < index; i++) {
                    deletedNode = deletedNode.next;
                }
            } else {
                deletedNode = tail;
                for (int i = size - 1; i > index; --i) {
                    deletedNode = deletedNode.prev;
                }
            }

            FunctionNode prevNode = deletedNode.prev; 
            FunctionNode nextNode = deletedNode.next; 

            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }

        size--;
        return deletedNode;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tabulated Func:[");
        FunctionNode point = head.next;
        for (int i = 0; i  < size; i++) {
            if (i == 0) {
                sb.append(head.toString());
            }
            else {
                sb.append(point.toString());
                point = point.next;
            }
            if (i < size - 1) {
                sb.append(',');
            }
        }
        sb.append("]");
        return sb.toString();
    }
}