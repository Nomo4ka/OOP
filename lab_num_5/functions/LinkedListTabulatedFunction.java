package functions;

import java.io.Serializable;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable {
    
    private static class FunctionNode implements Serializable {
        FunctionPoint p;
        private FunctionNode next;
        private FunctionNode prev;

        FunctionNode(FunctionPoint p) {
            this.p = p;
        }

        @Override
        public String toString() {
            return String.format("(%.2f; %.2f)", p.getX(), p.getY());
        }
    }

    int size = 0;
    FunctionNode head;
    FunctionNode tail;

    public LinkedListTabulatedFunction() {
        head = null;
        tail = null;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Должно быть не менее 2 точек!");
        }

        for (int i = 1; i < points.length; i++) {
            if (points[i].getX() <= points[i - 1].getX()) {
                throw new IllegalArgumentException("Точки должны быть упорядочены по возрастанию X!");
            }
        }
        
        head = null;
        tail = null;
        size = 0;

        for (FunctionPoint point : points) {
            addNodeToTail(new FunctionPoint(point));
        }
    }

    //5 таска
    // ------------------------------------------------------
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX && pointsCount == 2) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой!");
        }

        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; i++) {
            addNodeToTail(new FunctionPoint(leftX + i * step, 0));
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX && values.length == 2) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой!");
        }

        double step = (rightX - leftX) / (values.length - 1);

        for (int i = 0; i < values.length; i++) {
            addNodeToTail(new FunctionPoint(leftX + i * step, values[i]));
        }
    }

    public double getLeftDomainBorder() {
        return head.p.getX();
    }

    public double getRightDomainBorder() {
        return tail.p.getX();
    }

    public int getPointsCount() {
        return size;
    }

    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        FunctionNode node = getNodeByIndex(index);
        return new FunctionPoint(node.p);
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        FunctionNode node = getNodeByIndex(index);

        if (node.prev != null && point.getX() <= node.prev.p.getX()) {
            throw new IllegalArgumentException("X должен быть больше предыдущей точки!");
        }

        if (node.next != null && point.getX() >= node.next.p.getX()) {
            throw new IllegalArgumentException("X должен быть меньше следующей точки!");
        }

        node.p = new FunctionPoint(point);
    }
    
    public double getPointX(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        return getNodeByIndex(index).p.getX();
    }
    
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        FunctionNode node = getNodeByIndex(index);

        if (node.prev != null && x <= node.prev.p.getX()) {
            throw new InappropriateFunctionPointException("X должен быть больше предыдущей точки!");
        }

        if (node.next != null && x >= node.next.p.getX()) {
            throw new InappropriateFunctionPointException("X должен быть меньше следующей точки!");
        }

        node.p.setX(x);
    }

    public double getPointY(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        return getNodeByIndex(index).p.getY();
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        getNodeByIndex(index).p.setY(y);
    }
    //------------------------------------------------------
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        FunctionNode node = head;

        while (node.next != null) {
            FunctionPoint p1 = node.p;
            FunctionPoint p2 = node.next.p;

            if (x >= p1.getX() && x <= p2.getX()) {
                double t = (x - p1.getX()) / (p2.getX() - p1.getX());
                return p1.getY() + t * (p2.getY() - p1.getY());
            }

            node = node.next;
        }

        return Double.NaN;
    }

    public void deletePoint(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (size < 3) {
            throw new IllegalStateException("Нельзя удалить точку, если их меньше 3!");
        }

        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {

        FunctionNode node= head;
        int index = 0;

        while (node != null) {

            if (node.p.getX() == point.getX()) {
                throw new InappropriateFunctionPointException("Такая точка уже существует!");
            }

            if (point.getX() < node.p.getX()) {
                addNodeByIndex(index, point);
                return;
            }

            node = node.next;
            index++;
        }

        addNodeToTail(point);
    }
    //------------------------------------------------------
    private FunctionNode getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (index < size / 2) {
            FunctionNode p = head;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            return p;
        } else {
            FunctionNode p = tail;
            for (int i = size - 1; i > index; --i) {
                p = p.prev;
            }
            return p;
        }
    }

    private FunctionNode addNodeToTail(FunctionPoint p) {
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

    private FunctionNode addNodeToHead(FunctionPoint p) {
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

    private FunctionNode addNodeByIndex(int index, FunctionPoint p) {
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
    
    private FunctionNode deleteNodeByIndex(int index) {

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
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                sb.append(head.toString());
            } else {
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof TabulatedFunction))
            return false;

        if (obj instanceof LinkedListTabulatedFunction) {
            LinkedListTabulatedFunction other = (LinkedListTabulatedFunction) obj;

            if (this.size != other.size)
                return false;

            FunctionNode node1 = this.head;
            FunctionNode node2 = other.head;

            while (node1 != null) {
                if (!node1.p.equals(node2.p))
                    return false;
                node1 = node1.next;
                node2 = node2.next;
            }

            return true;
        }

        TabulatedFunction other = (TabulatedFunction) obj;
        if (this.size != other.getPointsCount())
            return false;

        FunctionNode node = this.head;
        for (int i = 0; i < size; i++) {
            if (!node.p.equals(other.getPoint(i)))
                return false;
            node = node.next;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = size;
        FunctionNode node = head;

        while (node != null) {
            hash = 31 * hash + node.p.hashCode();
            node = node.next;
        }

        return hash;
    }

    @Override
    public TabulatedFunction clone() {
        LinkedListTabulatedFunction cloned = new LinkedListTabulatedFunction();
        FunctionNode node = this.head;
        while (node != null) {
            cloned.addNodeToTail(new FunctionPoint(node.p));
            node = node.next;
        }
        return cloned;
    }
}