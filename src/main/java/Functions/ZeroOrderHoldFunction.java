package Functions;

import Listener.FunctionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.TreeMap;


public class ZeroOrderHoldFunction implements Function {

    private TreeMap<LocalDate, Double> valueMap;
    private List<FunctionListener> listeners;

    public ZeroOrderHoldFunction() {
        valueMap = new TreeMap<>();
        listeners = new ArrayList<>();
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

    @Override
    public void addListener(FunctionListener functionListener) {
        listeners.add(functionListener);
    }

    public void addValue(LocalDate date, Double value) {
        if (valueMap.containsKey(date)) {
            valueMap.put(date, getUpdatedValue(date, value));
        } else {
            valueMap.put(date, value);
        }
    }

    private Double getUpdatedValue(LocalDate date, Double value) {
        return valueMap.get(date) + value;
    }

}