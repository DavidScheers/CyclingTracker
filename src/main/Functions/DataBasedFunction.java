package main.Functions;

import main.Listener.FunctionListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBasedFunction implements Function {

    private Map<LocalDate, Double>  valueMap;
    private List<FunctionListener> listeners;

    public DataBasedFunction() {
        valueMap = new HashMap<>();
        listeners = new ArrayList<>();
    }

    @Override
    public double getValue(LocalDate inputDate) {
        double output;
        if (valueMap.containsKey(inputDate)) {
            output = valueMap.get(inputDate);
        } else {
            output = 0;
        }
        return output;
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

    private double getUpdatedValue(LocalDate date, Double value) {
        return valueMap.get(date) + value;
    }

}
