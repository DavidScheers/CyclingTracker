package main.Functions;

import main.Listener.FunctionListener;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by davids on 28/02/2017.
 */
public class DifferenceFunction implements Function {

    private Function firstFunction;
    private Function secondFunction;
    private List<FunctionListener> listeners;

    public DifferenceFunction(Function firstFunction, Function secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double getValue(LocalDate date) {
        notifyListeners();
        return firstFunction.getValue(date) - secondFunction.getValue(date);
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
