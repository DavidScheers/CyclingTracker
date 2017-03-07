package Functions;


import Listener.FunctionListener;

import java.time.LocalDate;

public interface Function {

    double getValue(LocalDate date);
    void addListener(FunctionListener functionListener);
}

