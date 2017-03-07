package Functions;

import java.time.LocalDate;

public class ShiftedFunction extends FunctionBaseImplementation implements Function {

    private DifferenceFunction differenceFunction;
    private int datesToShift;

    public ShiftedFunction(DifferenceFunction differenceFunction, int datesToShift) {
        super();
        this.differenceFunction = differenceFunction;
        this.datesToShift = datesToShift;
    }

    @Override
    public double getValue(LocalDate date) {
        return differenceFunction.getValue(date.minusDays(datesToShift));
    }

}

