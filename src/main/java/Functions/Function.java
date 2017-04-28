package functions;


import listener.FunctionListener;

import java.time.LocalDate;

public interface Function {

    double getValue(LocalDate date);
    void addListener(FunctionListener functionListener);
}

