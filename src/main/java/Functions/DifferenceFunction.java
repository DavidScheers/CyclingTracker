package Functions;

import java.time.LocalDate;

public class DifferenceFunction extends FunctionBaseImplementation {

    private Function firstFunction;
    private Function secondFunction;

    public DifferenceFunction(Function firstFunction, Function secondFunction) {
        super();
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double getValue(LocalDate date) {
        return firstFunction.getValue(date) - secondFunction.getValue(date);
    }

}
