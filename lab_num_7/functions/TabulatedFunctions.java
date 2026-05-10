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
import java.lang.reflect.*;

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
        return createTabulatedFunction(points);
    }

    public static TabulatedFunction tabulate(
            Class<? extends TabulatedFunction> functionClass,
            Function function,
            double leftX,
            double rightX,
            int pointsCount) {
        if(leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder() ){
            throw new IllegalArgumentException("Р“СЂР°РЅРёС†С‹ С‚Р°Р±СѓР»РёСЂРѕРІР°РЅРёСЏ РґРѕР»Р¶РЅС‹ Р±С‹С‚СЊ РІРЅСѓС‚СЂРё РѕР±Р»Р°СЃС‚Рё РѕРїСЂРµРґРµР»РµРЅРёСЏ С„СѓРЅРєС†РёРё!");
        }

        double st = (rightX - leftX) / (pointsCount - 1);
        FunctionPoint[] points = new FunctionPoint[pointsCount];

        for (int i = 0; i < pointsCount; i++) {
            double x  = leftX + st * i;
            double y = function.getFunctionValue(x);
            points[i] = new FunctionPoint(x, y);
        }
        return createTabulatedFunction(functionClass, points);
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
        
        return createTabulatedFunction(points);
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
        return createTabulatedFunction(points);
    }

    public static void serializeTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(function);
        oos.flush();
    }

    public static TabulatedFunction deserializeTabulatedFunction(InputStream in)
            throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (TabulatedFunction) ois.readObject();
    }
    
    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory) {
        TabulatedFunctions.factory = factory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return factory.createTabulatedFunction(leftX, rightX, values);
    }
    
    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return factory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(
            Class<? extends TabulatedFunction> functionClass,
            double leftX,
            double rightX,
            double[] values) {
        return createTabulatedFunctionByClass(
                functionClass,
                new Class<?>[] { double.class, double.class, double[].class },
                leftX,
                rightX,
                values);
    }

    public static TabulatedFunction createTabulatedFunction(
            Class<? extends TabulatedFunction> functionClass,
            FunctionPoint[] points) {
        return createTabulatedFunctionByClass(
                functionClass,
                new Class<?>[] { FunctionPoint[].class },
                (Object) points);
    }

    public static TabulatedFunction createTabulatedFunction(
            Class<? extends TabulatedFunction> functionClass,
            double leftX,
            double rightX,
            int pointsCount) {
        return createTabulatedFunctionByClass(
                functionClass,
                new Class<?>[] { double.class, double.class, int.class },
                leftX,
                rightX,
                pointsCount);
    }

    private static TabulatedFunction createTabulatedFunctionByClass(
            Class<? extends TabulatedFunction> functionClass,
            Class<?>[] parameterTypes,
            Object... args) {
        if (functionClass == null) {
            throw new NullPointerException("Function class is null");
        }

        try {
            Constructor<? extends TabulatedFunction> constructor = functionClass.getConstructor(parameterTypes);
            return constructor.newInstance(args);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
