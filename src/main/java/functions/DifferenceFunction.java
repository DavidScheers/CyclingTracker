package functions;

import listener.FunctionListener;

import java.time.LocalDate;

public class DifferenceFunction extends FunctionBaseImplementation implements FunctionListener {

    private Function firstFunction;
    private Function secondFunction;

    public DifferenceFunction(Function firstFunction, Function secondFunction) {
        super();
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
        subscribeToListeners();
    }

    @Override
    public double getValue(LocalDate date) {
        return firstFunction.getValue(date) - secondFunction.getValue(date);
    }

    @Override
    public void changeDetected() {
        notifyListeners(listeners);
    }

    public void subscribeToListeners() {
        firstFunction.addListener(this);
        secondFunction.addListener(this);
    }

}
