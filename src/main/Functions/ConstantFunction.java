package main.Functions;

import main.Listener.FunctionListener;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by davids on 27/02/2017.
 */
public class ConstantFunction implements Function {

    private double value;
    private List<FunctionListener> listeners;

    @Override
    public double getValue(LocalDate date) {
        return value;
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
