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
            if (isSupported(function, managedSerializer)) {
                ByteBuffer indexByte = createIndexIndicator(managedSerializer);
                byte[] serialised = managedSerializer.serialize(function);

                ByteArrayOutputStream b = concatByteArrays(indexByte, serialised);

                return b.toByteArray();
            }
        }
        throw new UnsupportedOperationException();
    }

    private boolean isSupported(Function function, Serializer<Function> managedSerializer) {
        return managedSerializer.getSupportedTypes().contains(function.getClass());
    }

    private ByteArrayOutputStream concatByteArrays(ByteBuffer indexByte, byte[] serialised) {
        ByteBuffer wrappedSerialised = ByteBuffer.wrap(serialised);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(indexByte.get());
        b.write(wrappedSerialised.get());
        return b;
    }

    private ByteBuffer createIndexIndicator(Serializer<Function> managedSerializer) {
        int index = managedSerializers.indexOf(managedSerializer);
        byte[] byteArray = new byte[2];
        return ByteBuffer.wrap(byteArray).putShort((short) index);
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
