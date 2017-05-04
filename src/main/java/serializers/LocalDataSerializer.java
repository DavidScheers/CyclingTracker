package serializers;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class LocalDataSerializer implements Serializer<LocalDate> {

    @Override
    public byte[] serialize(LocalDate date) {
        long value = date.toEpochDay();
        byte[] byteArray = new byte[8];
        ByteBuffer.wrap(byteArray).putLong(value);
        return byteArray;
    }

    @Override
    public LocalDate deserialize(byte[] byteArray) {
        long value = ByteBuffer.wrap(byteArray).getLong();
        return LocalDate.ofEpochDay(value);
    }

    @Override
    public Set<Class<? extends LocalDate>> getSupportedTypes() {
        Set<Class<? extends LocalDate>> supportedTypes = new HashSet<>();
        supportedTypes.add(LocalDate.class);
        return supportedTypes;
    }
}
