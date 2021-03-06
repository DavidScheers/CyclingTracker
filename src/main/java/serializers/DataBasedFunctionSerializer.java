package serializers;

import functions.DataBasedFunction;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataBasedFunctionSerializer implements Serializer<DataBasedFunction> {

    private LocalDateSerializer dateSerializer;

    DataBasedFunctionSerializer(LocalDateSerializer dateSerializer) {
        this.dateSerializer = dateSerializer;
    }

    @Override
    public byte[] serialize(DataBasedFunction function) {
        Map<LocalDate, Double> valueMap = function.getValueMap();

        int totalDateLength = getNumberOfBytesForDates(valueMap);
        byte[] bytes = new byte[valueMap.size() * 12 + totalDateLength + 4];

        ByteBuffer wrappedBytes = ByteBuffer.wrap(bytes);
        wrappedBytes.putInt(valueMap.size());

        valueMap.keySet().stream()
                .forEach(localDate ->
                        wrappedBytes.putInt(getNumberOfBytesForDate(localDate))
                                .put(dateSerializer.serialize(localDate))
                                .putDouble(valueMap.get(localDate)));
        return bytes;
    }

    @Override
    public DataBasedFunction deserialize(byte[] byteArray) {
        HashMap<LocalDate, Double> valueMap = new HashMap<>();
        ByteBuffer wrap = ByteBuffer.wrap(byteArray);
        int numberOfValues = wrap.getInt();

        for (int i = 0; i < numberOfValues; i++) {
            int sizeOfDate = wrap.getInt();
            byte[] tempArray = new byte[sizeOfDate];
            wrap.get(tempArray);
            valueMap.put(dateSerializer.deserialize(tempArray), wrap.getDouble());
        }
        return new DataBasedFunction(valueMap);
    }

    @Override
    public Set<Class<? extends DataBasedFunction>> getSupportedTypes() {
        Set<Class<? extends DataBasedFunction>> supportedTypes = new HashSet<>();
        supportedTypes.add(DataBasedFunction.class);
        return supportedTypes;
    }

    private int getNumberOfBytesForDate(LocalDate date) {
        return dateSerializer.serialize(date).length;
    }

    private int getNumberOfBytesForDates(Map<LocalDate, Double> valueMap) {
        int totalDateLength = 0;
        for (LocalDate localDate : valueMap.keySet()) {
            totalDateLength += dateSerializer.serialize(localDate).length;
        }
        return totalDateLength;
    }
}
