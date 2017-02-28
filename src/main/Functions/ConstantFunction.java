package main.Functions;

import java.time.LocalDate;

/**
 * Created by davids on 27/02/2017.
 */
public class ConstantFunction implements Function {

    private double value;

    @Override
    public double getValue(LocalDate date) {
        return value;
    }

    public ConstantFunction(double value) {
        this.value = value;
    }
}
