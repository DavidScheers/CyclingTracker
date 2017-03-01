package main.Functions;

import java.time.LocalDate;

/**
 * Created by davids on 1/03/2017.
 */
public class ShiftedFunction implements Function {

    private DifferenceFunction differenceFunction;
    private int datesToShift;

    public ShiftedFunction(DifferenceFunction differenceFunction, int datesToShift) {
        this.differenceFunction = differenceFunction;
        this.datesToShift = datesToShift;
    }

    @Override
    public double getValue(LocalDate date) {
        return differenceFunction.getValue(date.plusDays(datesToShift));
    }
}

