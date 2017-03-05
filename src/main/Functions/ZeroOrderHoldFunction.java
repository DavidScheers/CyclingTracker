package main.Functions;

import main.Listener.FunctionListener;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by davids on 2/03/2017.
 */
public class ZeroOrderHoldFunction implements Function {

    private TreeMap<LocalDate, Double> valueMap;
    private List<FunctionListener> listeners;

    public ZeroOrderHoldFunction(Map<LocalDate, Double> valueMap) {
        this.valueMap = new TreeMap<>(valueMap);
    }

    @Override
    public double getValue(LocalDate date) {
        double result;
        if (valueMap.isEmpty() || date.isBefore(valueMap.firstKey())) {
            result = 0.0;
        } else {
            result = valueMap.containsKey(date) ? valueMap.get(date) : valueMap.get(valueMap.floorKey(date));
        }
        notifyListeners();
        return result;
    }

    @Override
    public void addListener(FunctionListener functionListener) {
        listeners.add(functionListener);
    }

    private void notifyListeners() {
        for (FunctionListener listener : listeners) {
            listener.changeDetected();
        }
    }
}