package functions;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class LinkedListTabulatedFunctionExternalizable
        extends LinkedListTabulatedFunction
        implements Externalizable {

    public LinkedListTabulatedFunctionExternalizable() {
        super();
    }

    public LinkedListTabulatedFunctionExternalizable(FunctionPoint[] points) {
        super(points);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        int count = getPointsCount();
        out.writeInt(count);

        for (int i = 0; i < count; i++) {
            out.writeDouble(getPointX(i));
            out.writeDouble(getPointY(i));
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int count = in.readInt();

        if (count < 2) {
            throw new IOException("Табулированная функция должна содержать минимум 2 точки");
        }

        for (int i = 0; i < count; i++) {
            double x = in.readDouble();
            double y = in.readDouble();

            try {
                addPoint(new FunctionPoint(x, y));
            } catch (InappropriateFunctionPointException e) {
                throw new IOException("Ошибка восстановления точки: " + e.getMessage(), e);
            }
        }
    }
}