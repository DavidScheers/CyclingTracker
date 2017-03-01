package main.Functions;

import java.time.LocalDate;

public class PerformanceFunction implements Function {

    private Function tssFunction;
    private double decayParameter;
    private LocalDate startDate;

    public PerformanceFunction(Function tssFunction, double decayParameter, LocalDate startDate) {
        this.tssFunction = tssFunction;
        this.decayParameter = decayParameter;
        this.startDate = startDate;
    }

    @Override
    public double getValue(LocalDate date) {
        if (date.isBefore(startDate)) {
            return 0.0;
        } else {
            return getValueYesterday(date) + (getTss(date) - getValueYesterday(date))*getFactor();
        }
    }

    private double getValueYesterday(LocalDate date) {
        return getValue(date.minusDays(1));
    }

    private double getTss(LocalDate date) {
        return tssFunction.getValue(date);
    }

    private double getFactor() {
        return (1-Math.exp(-1/decayParameter));
    }

}
