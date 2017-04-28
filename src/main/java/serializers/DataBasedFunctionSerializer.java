package serializers;

import functions.DataBasedFunction;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.*;

public class DataBasedFunctionSerializer implements Serializer<DataBasedFunction> {

    private LocalDataSerializer dateSerializer;

    public DataBasedFunctionSerializer(LocalDataSerializer dateSerializer) {
        this.dateSerializer = dateSerializer;
    }

    @Override
    public byte[] serialize(DataBasedFunction function) {
        Map<LocalDate, Double> valueMap = function.getValueMap();

        int totalDateLength = getNumberOfBytesForDates(valueMap);
        byte[] bytes = new byte[valueMap.size()*12 + totalDateLength + 4];

        ByteBuffer wrappedBytes = ByteBuffer.wrap(bytes);
        wrappedBytes.putInt(valueMap.size());
        for (LocalDate localDate : valueMap.keySet()) {
            wrappedBytes.putInt(getNumberOfBytesForDate(localDate));
            wrappedBytes.put(dateSerializer.serialize(localDate));
            wrappedBytes.putDouble(valueMap.get(localDate));
        }
        return bytes;
    }

    @Override
    public DataBasedFunction deserialize(byte[] byteArray) {
        HashMap<LocalDate, Double> valueMap = new HashMap<>();
        ByteBuffer wrap = ByteBuffer.wrap(byteArray);
        int numberOfValues = wrap.getInt();

        for (int i = 0; i < numberOfValues ; i++) {
            int sizeOfDate = wrap.getInt();
            byte[] tempArray = new byte[sizeOfDate];
            wrap.get(tempArray);
            valueMap.put(dateSerializer.deserialize(tempArray), wrap.getDouble());
        }
        return new DataBasedFunction(valueMap);


    }

    @Override
    public Set<Class<? extends DataBasedFunction>> getSupportedTypes() {
        return null;
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
