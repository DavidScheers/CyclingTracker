package serializers;

import functions.ConstantFunction;
import functions.DataBasedFunction;
import functions.Function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FunctionSerializerManager implements Serializer<Function> {

    private List<Serializer<Function>> managedSerializers;

    @Override
    public byte[] serialize(Function function) {
        for (Serializer<Function> managedSerializer : managedSerializers) {
            if (managedSerializer.getSupportedTypes().contains(function.getClass())) {
               return managedSerializer.serialize(function);
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Function deserialize(byte[] byteArray) {
        return null;
    }

    @Override
    public Set<Class<? extends Function>> getSupportedTypes() {
        Set<Class<? extends Function>> supportedTypes = new HashSet<>();
        supportedTypes.add(ConstantFunction.class);
        supportedTypes.add(DataBasedFunction.class);
        return supportedTypes;
    }
}
