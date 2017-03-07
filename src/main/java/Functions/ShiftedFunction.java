package Functions;


import Listener.FunctionListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davids on 1/03/2017.
 */
public class ShiftedFunction implements Function {

    private DifferenceFunction differenceFunction;
    private int datesToShift;
    private List<FunctionListener> listeners;

    public ShiftedFunction(DifferenceFunction differenceFunction, int datesToShift) {
        this.differenceFunction = differenceFunction;
        this.datesToShift = datesToShift;
        listeners = new ArrayList<>();
    }

    @Override
    public double getValue(LocalDate date) {
        return differenceFunction.getValue(date.minusDays(datesToShift));
    }

    @Override
    public void addListener(FunctionListener functionListener) {
        listeners.add(functionListener);
    }

}
