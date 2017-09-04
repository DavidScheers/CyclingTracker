package functions;

import listener.FunctionListener;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionBaseImplementation implements Function {

    List<FunctionListener> listeners;

    FunctionBaseImplementation() {
        this.listeners = new ArrayList<>();
    }

    void notifyListeners(List<FunctionListener> listeners) {
        listeners.stream()
                .forEach(FunctionListener::changeDetected);
    }

    @Override
    public void addListener(FunctionListener functionListener) {
        listeners.add(functionListener);
    }
}
