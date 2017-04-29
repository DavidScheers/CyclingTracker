package functions;

import listener.FunctionListener;

import java.time.LocalDate;

public class ShiftedFunction extends FunctionBaseImplementation implements FunctionListener {

    private DifferenceFunction differenceFunction;
    private int datesToShift;

    public ShiftedFunction(DifferenceFunction differenceFunction, int datesToShift) {
        super();
        this.differenceFunction = differenceFunction;
        this.datesToShift = datesToShift;
        subscribeToListeners();
    }

    @Override
    public double getValue(LocalDate date) {
        return differenceFunction.getValue(date.minusDays(datesToShift));
    }

    private void subscribeToListeners() {
        differenceFunction.addListener(this);
    }

    @Override
    public void changeDetected() {
        notifyListeners(listeners);
    }
}

