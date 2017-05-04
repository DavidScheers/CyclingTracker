package serializers;


import functions.ZeroOrderHoldFunction;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeMap;

import static com.google.common.collect.Maps.newTreeMap;
import static com.google.common.collect.Sets.newHashSet;

public class ZeroOrderHoldSerializer implements Serializer<ZeroOrderHoldFunction> {

    private LocalDateSerializer localDateSerializer;

    public ZeroOrderHoldSerializer(LocalDateSerializer localDateSerializer) {
        this.localDateSerializer = localDateSerializer;
    }

    @Override
    public byte[] serialize(ZeroOrderHoldFunction function) {
        TreeMap<LocalDate, Double> valueMap = function.getValueMap();

        int totalDateLength = getNumberOfBytesForDates(valueMap);
        byte[] byteArray = new byte[valueMap.size() * 12 + totalDateLength + 4];

        ByteBuffer wrappedBytes = ByteBuffer.wrap(byteArray);
        wrappedBytes.putInt(valueMap.size());
        for (LocalDate localDate : valueMap.keySet()) {
            wrappedBytes.putInt(getNumberOfBytesForDate(localDate));
            wrappedBytes.put(localDateSerializer.serialize(localDate));
            wrappedBytes.putDouble(valueMap.get(localDate));
        }
        return byteArray;
    }

    @Override
    public ZeroOrderHoldFunction deserialize(byte[] byteArray) {
        TreeMap<LocalDate, Double> valueMap = newTreeMap();
        ByteBuffer wrappedBytes = ByteBuffer.wrap(byteArray);
        int numberOfValues = wrappedBytes.getInt();

        for (int i = 0; i < numberOfValues; i++) {
            int sizeOfDate = wrappedBytes.getInt();
            byte[] tempArray = new byte[sizeOfDate];
            wrappedBytes.get(tempArray);
            valueMap.put(localDateSerializer.deserialize(tempArray), wrappedBytes.getDouble());
        }
        return new ZeroOrderHoldFunction(valueMap);
    }

    @Override
    public Set<Class<? extends ZeroOrderHoldFunction>> getSupportedTypes() {
        Set<Class<? extends  ZeroOrderHoldFunction>> supportedTypes = newHashSet();
        supportedTypes.add(ZeroOrderHoldFunction.class);
        return supportedTypes;
    }

    private int getNumberOfBytesForDate(LocalDate localDate) {
        return localDateSerializer.serialize(localDate).length;
    }

    private int getNumberOfBytesForDates(TreeMap<LocalDate, Double> valueMap) {
        int totalDateLength = 0;
        for (LocalDate localDate : valueMap.keySet()) {
            totalDateLength += localDateSerializer.serialize(localDate).length;
        }
        return totalDateLength;
    }
}
