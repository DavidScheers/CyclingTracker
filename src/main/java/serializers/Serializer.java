package serializers;

import java.util.Set;

public interface Serializer<T> {

    byte[] serialize(T function);

    T deserialize(byte[] byteArray);

    Set<Class<?extends T>> getSupportedTypes();
}
