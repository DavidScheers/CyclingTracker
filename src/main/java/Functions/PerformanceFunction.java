package Functions;

import Listener.FunctionListener;

import java.time.LocalDate;

public class PerformanceFunction extends FunctionBaseImplementation implements FunctionListener {

    private Function tssFunction;
    private double decayParameter;
    private LocalDate startDate;

    public PerformanceFunction(Function tssFunction, double decayParameter, LocalDate startDate) {
        super();
        this.tssFunction = tssFunction;
        this.decayParameter = decayParameter;
        this.startDate = startDate;
        subscribeToListeners();
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
        return result;
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

    private void subscribeToListeners() {
        tssFunction.addListener(this);
    }


    @Override
    public void changeDetected() {
        notifyListeners(listeners);
    }
}
