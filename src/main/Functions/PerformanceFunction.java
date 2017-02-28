package main.Functions;

import java.time.LocalDate;

/**
 * Created by davids on 27/02/2017.
 */

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
            double valueYesterday = getValue(date.minusDays(1));
            double tss = tssFunction.getValue(date);
            double factor = (1-Math.exp(-1/decayParameter));
            return valueYesterday + (tss - valueYesterday)*factor;
        }
    }

}
