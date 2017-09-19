package functions;

import listener.FunctionListener;

import java.time.LocalDate;

public class ShiftedFunction extends FunctionBaseImplementation implements FunctionListener {

    private Function functionToShift;
    private int datesToShift;

    public ShiftedFunction(Function functionToShift, int datesToShift) {
        super();
        this.functionToShift = functionToShift;
        this.datesToShift = datesToShift;
        subscribeToListeners();
    }

    private ShiftedFunction() {
    }

    @Override
    public double getValue(LocalDate date) {
        return functionToShift.getValue(date.minusDays(datesToShift));
    }

    private void subscribeToListeners() {
        functionToShift.addListener(this);
    }

    @Override
    public void changeDetected() {
        notifyListeners(listeners);
    }
}

