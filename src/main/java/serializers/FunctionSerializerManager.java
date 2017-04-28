package serializers;

import functions.ConstantFunction;
import functions.DataBasedFunction;
import functions.Function;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FunctionSerializerManager implements Serializer<Function> {

    private List<Serializer<Function>> managedSerializers;

    @Override
    public byte[] serialize(Function function) {
        for (Serializer<Function> managedSerializer : managedSerializers) {
            if (managedSerializer.getSupportedTypes().contains(function.getClass())) {
                int index = managedSerializers.indexOf(managedSerializer);
                byte[] byteArray = new byte[2];
                ByteBuffer indexByte = ByteBuffer.wrap(byteArray).putShort((short) index);
                //byte[] sum = indexByte.get() + managedSerializer.serialize(function);
                byte[] serialised = managedSerializer.serialize(function);
                ByteBuffer wrappedSerialised = ByteBuffer.wrap(serialised);
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                b.write(indexByte.get());
                b.write(wrappedSerialised.get());

                return b.toByteArray();
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Function deserialize(byte[] byteArray) {
        return managedSerializers.get(getIndex(byteArray)).deserialize(byteArray);
    }

    private short getIndex(byte[] byteArray) {
        short[] tempArray = new short[2];
        return ByteBuffer.wrap(byteArray).getShort();
    }

    @Override
    public Set<Class<? extends Function>> getSupportedTypes() {
        Set<Class<? extends Function>> supportedTypes = new HashSet<>();
        supportedTypes.add(ConstantFunction.class);
        supportedTypes.add(DataBasedFunction.class);
        return supportedTypes;
    }
}
