package main.Functions;

import java.time.LocalDate;

/**
 * Created by davids on 28/02/2017.
 */
public class DifferenceFunction implements Function {

    private Function fitnessFunction;
    private Function fatigueFunction;

    public DifferenceFunction(Function fitnessFunction, Function fatigueFunction) {
        this.fitnessFunction = fitnessFunction;
        this.fatigueFunction = fatigueFunction;
    }

    @Override
    public double getValue(LocalDate date) {
        double value = fitnessFunction.getValue(date.minusDays(1)) - fatigueFunction.getValue(date.minusDays(1));
        return value;
    }
}
