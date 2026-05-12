package functions;

import java.io.*;

public class ArrayTabulatedFunctionExternalizable
        extends ArrayTabulatedFunction
        implements Externalizable {

    public ArrayTabulatedFunctionExternalizable() {
        super(new FunctionPoint[] {
                new FunctionPoint(0,0),
                new FunctionPoint(1,0)
        });
    }

    public ArrayTabulatedFunctionExternalizable(FunctionPoint[] points) {
        super(points);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getPointsCount());
        for (int i = 0; i < getPointsCount(); i++) {
            out.writeDouble(getPointX(i));
            out.writeDouble(getPointY(i));
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        int count = in.readInt();
        FunctionPoint[] pts = new FunctionPoint[count];

        for (int i = 0; i < count; i++) {
            pts[i] = new FunctionPoint(in.readDouble(), in.readDouble());
        }

        ArrayTabulatedFunction tmp = new ArrayTabulatedFunction(pts);

        for (int i = 0; i < tmp.getPointsCount(); i++) {
            try {
                this.setPoint(i, tmp.getPoint(i));
            } catch (Exception ignored) {}
        }
    }
}