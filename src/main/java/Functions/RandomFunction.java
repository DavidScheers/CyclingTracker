package functions;

import listener.FunctionListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by davids on 27/02/2017.
 */
public class RandomFunction implements Function {

    private List<FunctionListener> listeners;

    @Override
    public double getValue(LocalDate date) {
        Random rand = new Random();
        return rand.nextDouble();
    }

    @Override
    public void addListener(FunctionListener functionListener) {
        listeners.add(functionListener);
    }

    public RandomFunction() {
        listeners = new ArrayList<>();
    }
}
