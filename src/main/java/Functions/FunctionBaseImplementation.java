package Functions;

import Listener.FunctionListener;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionBaseImplementation implements Function {

    List<FunctionListener> listeners;

    public FunctionBaseImplementation() {
        this.listeners = new ArrayList<>();
    }

    void notifyListeners(List<FunctionListener> listeners) {
        for (FunctionListener listener : listeners) {
            listener.changeDetected();
        }
    }

    @Override
    public void addListener(FunctionListener functionListener) {
        listeners.add(functionListener);
    }
}
