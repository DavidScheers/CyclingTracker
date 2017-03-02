package main.Functions;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by davids on 2/03/2017.
 */
public class ZeroOrderHoldFunction implements Function {

    private TreeMap<LocalDate, Double> valueMap;

    public ZeroOrderHoldFunction(Map<LocalDate, Double> valueMap) {
        this.valueMap = new TreeMap<>(valueMap);
    }

    @Override
    public double getValue(LocalDate date) {
        if (valueMap.isEmpty() || date.isBefore(valueMap.firstKey())) {
            return 0.0;
        } else {
            return valueMap.containsKey(date) ? valueMap.get(date) : valueMap.get(valueMap.floorKey(date));
        }
    }
}