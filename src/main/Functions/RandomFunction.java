package main.Functions;

import main.Listener.FunctionListener;

import java.time.LocalDate;
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
        notifyListeners();
        return rand.nextDouble();
    }

    @Override
    public void addListener(FunctionListener functionListener) {
        listeners.add(functionListener);
    }

    private void notifyListeners() {
        for (FunctionListener listener : listeners) {
            listener.changeDetected();
        }
    }
}
