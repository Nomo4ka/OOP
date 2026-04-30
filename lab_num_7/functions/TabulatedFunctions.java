package functions;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TabulatedFunctions {

    private TabulatedFunctions() {
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        if(leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder() ){
            throw new IllegalArgumentException("Границы табулирования должны быть внутри области определения функции!");
        }

        double st = (rightX - leftX) / (pointsCount - 1);
        FunctionPoint[] points = new FunctionPoint[pointsCount];

        for (int i = 0; i < pointsCount; i++) {
            double x  = leftX + st * i;
            double y = function.getFunctionValue(x);
            points[i] = new FunctionPoint(x, y);            
        }
        return new ArrayTabulatedFunction(points);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws java.io.IOException {
        DataOutputStream dos = new DataOutputStream(out);

        int count = function.getPointsCount();
        dos.writeInt(count);

        for(int i = 0; i < count; i++) {
            dos.writeDouble(function.getPointX(i));
            dos.writeDouble(function.getPointY(i));
        }

        dos.flush();

    }
    
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws java.io.IOException {
        DataInputStream dis = new DataInputStream(in);

        int count = dis.readInt();
        FunctionPoint[] points = new FunctionPoint[count];

        for(int i = 0; i < count; i++) {
            double x = dis.readDouble();
            double y = dis.readDouble();
            FunctionPoint newp = new FunctionPoint(x, y);
            points[i] = newp;
        }
        
        return new ArrayTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        int count = function.getPointsCount();
        out.write(count + "\n");

        for (int i = 0; i < count; i++) {
            out.write(function.getPointX(i) + " " + function.getPointY(i) + "\n");
        }

        out.flush();
    }
    
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(new BufferedReader(in));
        tokenizer.nextToken();
        int count = (int) tokenizer.nval;
        FunctionPoint[] points = new FunctionPoint[count];

        for (int i = 0; i < count; i++) {
            tokenizer.nextToken();
            double x = tokenizer.nval;
            tokenizer.nextToken();
            double y = tokenizer.nval;
            FunctionPoint newp = new FunctionPoint(x, y);
            points[i] = newp;
        }
        return new ArrayTabulatedFunction(points);
    }

    public static void serializeTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(function);
        oos.flush();
    }

    public static TabulatedFunction deserializeTabulatedFunction(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (TabulatedFunction) ois.readObject();
    }
  
}