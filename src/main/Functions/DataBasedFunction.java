package main.Functions;

import main.Listener.FunctionListener;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by davids on 27/02/2017.
 */
public class DataBasedFunction implements Function {

    private Map<LocalDate, Double>  valueMap;
    private List<FunctionListener> listeners;

    public DataBasedFunction(Map<LocalDate, Double> valueMap) {
        this.valueMap = valueMap;
    }

    @Override
    public double getValue(LocalDate inputDate) {
        double output;
        if (valueMap.containsKey(inputDate)) {
            output = valueMap.get(inputDate);
        } else {
            output = 0;
        }
        notifyListeners();
        return output;
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
