package main.Functions;

import main.Listener.FunctionListener;

import java.time.LocalDate;
import java.util.List;

public class PerformanceFunction implements Function {

    private Function tssFunction;
    private double decayParameter;
    private LocalDate startDate;
    private List<FunctionListener> listeners;

    public PerformanceFunction(Function tssFunction, double decayParameter, LocalDate startDate) {
        this.tssFunction = tssFunction;
        this.decayParameter = decayParameter;
        this.startDate = startDate;
    }

    @Override
    public double getValue(LocalDate date) {
        double result;
        if (date.isBefore(startDate)) {
            result = 0.0;
        } else {
            double valueYesterday = getValueYesterday(date);
            result = valueYesterday + (getTss(date) - valueYesterday)*getFactor();
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

    private double getValueYesterday(LocalDate date) {
        return this.getValue(date.minusDays(1));
    }

    private double getTss(LocalDate date) {
        return tssFunction.getValue(date);
    }

    private double getFactor() {
        return (1-Math.exp(-1/decayParameter));
    }

}
