package serializers;

import functions.ConstantFunction;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ConstantFunctionSerializer implements Serializer<ConstantFunction> {

    @Override
    public byte[] serialize(ConstantFunction function) {
        double value = function.getValue(LocalDate.now());
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    @Override
    public ConstantFunction deserialize(byte[] bytes) {
        double value = ByteBuffer.wrap(bytes).getDouble();
        return new ConstantFunction(value);
    }

    @Override
    public Set<Class<? extends ConstantFunction>> getSupportedTypes() {
        Set<Class<? extends ConstantFunction>> supportedTypes = new HashSet<>();
        supportedTypes.add(ConstantFunction.class);
        return supportedTypes;
    }
}
