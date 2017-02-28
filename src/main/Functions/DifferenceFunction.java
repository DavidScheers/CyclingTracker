package main.Functions;

import java.time.LocalDate;

/**
 * Created by davids on 28/02/2017.
 */
public class DifferenceFunction implements Function {

    private Function firstFunction;
    private Function secondFunction;

    public DifferenceFunction(Function firstFunction, Function secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double getValue(LocalDate date) {
        double value = firstFunction.getValue(date.minusDays(1)) - secondFunction.getValue(date.minusDays(1));
        return value;
    }
}
