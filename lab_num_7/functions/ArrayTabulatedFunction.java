package functions;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable {
    
    private FunctionPoint points[];
    private int pointsCount;
    private double leftX, rightX;
    private int capacity;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX && pointsCount == 2) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой!");
        }
        this.leftX = leftX;
        this.rightX = rightX;
        this.pointsCount = pointsCount;
        capacity = (pointsCount * 3)/2;
        points = new FunctionPoint[capacity];
        
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            FunctionPoint p = new FunctionPoint(leftX + i * step, 0);
            points[i] = p;
        } 
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX && values.length == 2) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой!");
        }
        this.leftX = leftX;
        this.rightX = rightX;
        this.pointsCount = values.length;
        capacity = (pointsCount * 3) / 2;
        points = new FunctionPoint[capacity];

        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < pointsCount; i++) {
            FunctionPoint p = new FunctionPoint(leftX + i * step, values[i]);
            points[i] = p;
        }
    }
    
    public ArrayTabulatedFunction(FunctionPoint[] points) {
        if (points.length < 2) {
            throw new IllegalArgumentException("Должно быть не менее 2 точек!");
        }

        for (int i = 1; i < points.length; i++) {
            if (points[i].getX() <= points[i - 1].getX()) {
                throw new IllegalArgumentException("Точки должны быть упорядочены по возрастанию X!");
            }
        }

        this.pointsCount = points.length;
        capacity = (pointsCount * 3) / 2;
        if (capacity < 2) {
            capacity = 2;
        }
        this.points = new FunctionPoint[capacity];
        for (int i = 0; i < pointsCount; i++) {
            this.points[i] = new FunctionPoint(points[i]);
        }
        this.leftX = this.points[0].getX();
        this.rightX = this.points[pointsCount - 1].getX();
    }

    public double getLeftDomainBorder() {
        return points[0].getX();
    }
    
    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    public double getFunctionValue(double x) {
        if (x >= leftX && x <= rightX) {
            for (int i = 0; i < pointsCount - 1; i++) {
                FunctionPoint p1 = points[i];
                FunctionPoint p2 = points[i + 1];
                if (x >= p1.getX() && x <= p2.getX()) {
                    double t = (x - p1.getX()) / (p2.getX() - p1.getX());
                    return p1.getY() + t * (p2.getY() - p1.getY());
                }
            }
        }
        return Double.NaN;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }
        return new FunctionPoint(points[index]);
    }

    public void setPoint(int index, FunctionPoint point)
            throws InappropriateFunctionPointException {

        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (index > 0 && point.getX() <= points[index - 1].getX()) {
            throw new InappropriateFunctionPointException("Точка должна быть больше предыдущей!");
        }

        if (index < pointsCount - 1 && point.getX() >= points[index + 1].getX()) {
            throw new InappropriateFunctionPointException("Точка должна быть меньше следующей!");
        }

        points[index] = new FunctionPoint(point);

        if (index == 0)
            leftX = points[0].getX();

        if (index == pointsCount - 1)
            rightX = points[pointsCount - 1].getX();
    }
    
    public double getPointX(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        FunctionPoint p = points[index];
        return p.getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (index > 0 && x <= points[index - 1].getX()) {
            throw new InappropriateFunctionPointException("x должен быть больше предыдущей точки!");
        }

        if (index < pointsCount - 1 && x >= points[index + 1].getX()) {
            throw new InappropriateFunctionPointException("x должен быть меньше следующей точки!");
        }

        FunctionPoint newp = new FunctionPoint(x, points[index].getY());
        points[index] = newp;
        points[index].setX(x);

        if (index == 0) {
            leftX = points[0].getX();
        }
        if (index == pointsCount - 1) {
            rightX = points[pointsCount - 1].getX();
        }
    }

    public double getPointY(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }
        FunctionPoint p = points[index];
        return p.getY();
    }
    
    public void setPointY(int index, double y) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        points[index].setY(y);
    }

    public void deletePoint(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне допустимого диапазона!");
        }

        if (pointsCount <= 2) {
            throw new IllegalStateException("Нельзя удалить точку — останется меньше 2 точек");
        }

        System.arraycopy(points, index + 1, points, index, pointsCount - index - 1);
        pointsCount--;

        leftX = points[0].getX();
        rightX = points[pointsCount - 1].getX();
    }

    //arr = {{1,2},{2,3},{2.5,1},{3,4}}
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        for (int i = 0; i < pointsCount; i++) {
            if (points[i].getX() == point.getX()) {
                throw new InappropriateFunctionPointException("Абсцисса добавляемой точки уже существует");
            }
        }

        int index = rangeCheckForAdd(point.getX());
        if (pointsCount == points.length)
            points = grow(); 

        System.arraycopy(points, index, points, index + 1, pointsCount - index);

        points[index] = point;
        ++pointsCount;
        leftX = points[0].getX();
        rightX = points[pointsCount - 1].getX();
        // pointsCount = s + 1;
    }
    
    private int rangeCheckForAdd(double x) {
        for (int i = 0; i < pointsCount; i++) {
            if (x <= points[i].getX()) {
                return i;
            }
        }
        return pointsCount;
    }

    private FunctionPoint[] grow() {
        int newcap = (capacity * 3) / 2;
        FunctionPoint[] newpPoints = new FunctionPoint[newcap];
        System.arraycopy(points, 0, newpPoints, 0, pointsCount);
        capacity = newcap;
        return newpPoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tabulated Func:[");
        for (int i = 0; i < pointsCount; i++) {
            sb.append(points[i].toString());
            if (i < pointsCount - 1)
                sb.append(", ");
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

        if (obj instanceof ArrayTabulatedFunction) {
            ArrayTabulatedFunction other = (ArrayTabulatedFunction) obj;

            if (this.pointsCount != other.pointsCount)
                return false;

            for (int i = 0; i < pointsCount; i++) {
                if (!this.points[i].equals(other.points[i]))
                    return false;
            }
        }

        if (obj instanceof TabulatedFunction) {
            TabulatedFunction other = (TabulatedFunction) obj;

            if (this.pointsCount != other.getPointsCount())
                return false;

            for (int i = 0; i < pointsCount; i++) {
                if (!this.points[i].equals(other.getPoint(i)))
                    return false;
            }
        }
        return true;
    }

    @Override
    public TabulatedFunction clone() {
        FunctionPoint[] copiedPoints = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            copiedPoints[i] = new FunctionPoint(points[i]);
        }
        return new ArrayTabulatedFunction(copiedPoints);
    }
    
    @Override
    public int hashCode() {
        int hash = pointsCount;
        for (int i = 0; i < pointsCount; i++) {
            hash ^= points[i].hashCode();
        }
        return hash;
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < pointsCount;
            }

            @Override
            public FunctionPoint next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Нет больше точек для итерации!");
                }
                return new FunctionPoint(points[currentIndex++]);
            }
        };
    }

}
