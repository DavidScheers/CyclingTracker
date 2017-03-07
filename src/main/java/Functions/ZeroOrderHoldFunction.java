package Functions;

import java.time.LocalDate;
import java.util.TreeMap;


public class ZeroOrderHoldFunction extends FunctionBaseImplementation {

    private TreeMap<LocalDate, Double> valueMap;

    public ZeroOrderHoldFunction() {
        super();
        valueMap = new TreeMap<>();
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
        if (valueMap.containsKey(date)) {
            valueMap.put(date, getUpdatedValue(date, value));
        } else {
            valueMap.put(date, value);
        }
        notifyListeners(listeners);
    }

    private Double getUpdatedValue(LocalDate date, Double value) {
        return valueMap.get(date) + value;
    }

}