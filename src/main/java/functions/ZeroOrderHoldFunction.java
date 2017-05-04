package functions;

import java.time.LocalDate;
import java.util.TreeMap;

import static com.google.common.collect.Maps.newTreeMap;


public class ZeroOrderHoldFunction extends FunctionBaseImplementation {

    private TreeMap<LocalDate, Double> valueMap;

    public ZeroOrderHoldFunction() {
        super();
        valueMap = new TreeMap<>();
    }

    public ZeroOrderHoldFunction(TreeMap<LocalDate, Double> valueMap) {
        this.valueMap = newTreeMap(valueMap);
    }

    public TreeMap<LocalDate, Double> getValueMap() {
        return valueMap;
    }

    @Override
    public double getValue(LocalDate date) {
        double result;
        if (valueMap.isEmpty() || date.isBefore(valueMap.firstKey())) {
            result = 0.0;
        } else {
            result = valueMap.containsKey(date) ? valueMap.get(date) : valueMap.get(valueMap.floorKey(date));
        }
        return result;
    }

    public void addValue(LocalDate date, Double value) {
        addToMap(date, value);
        notifyListeners(listeners);
    }

    private void addToMap(LocalDate date, Double value) {
        if (valueMap.containsKey(date)) {
            valueMap.put(date, getUpdatedValue(date, value));
        } else {
            valueMap.put(date, value);
        }
    }

    private Double getUpdatedValue(LocalDate date, Double value) {
        return valueMap.get(date) + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZeroOrderHoldFunction that = (ZeroOrderHoldFunction) o;

        return valueMap != null ? valueMap.equals(that.valueMap) : that.valueMap == null;
    }

    @Override
    public int hashCode() {
        return valueMap != null ? valueMap.hashCode() : 0;
    }
}