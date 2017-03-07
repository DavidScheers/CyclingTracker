package Functions;

import java.time.LocalDate;

public class DifferenceFunction extends FunctionBaseImplementation implements Function {

    private Function firstFunction;
    private Function secondFunction;

    public DifferenceFunction(Function firstFunction, Function secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double getValue(LocalDate date) {
        return firstFunction.getValue(date) - secondFunction.getValue(date);
    }

}
