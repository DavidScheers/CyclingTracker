package Functions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataBasedFunction extends FunctionBaseImplementation {

    private Map<LocalDate, Double>  valueMap;

    public DataBasedFunction() {
        super();
        valueMap = new HashMap<>();
    }

    @Override
    public double getValue(LocalDate inputDate) {
        double output = 0;
        if (valueMap.containsKey(inputDate)) {
            output = valueMap.get(inputDate);
        }
        return output;
    }

    public void addValue(LocalDate date, Double value) {
        addToMap(date, value);
        notifyListeners(listeners);
    }

    private void addToMap(LocalDate date, Double value) {
        if (valueMap.containsKey(date)) {
            valueMap.put(date, getUpdatedValue(date, value));
        } else {
            valueMap.put(date, value);
        }
    }

    private double getUpdatedValue(LocalDate date, Double value) {
        return valueMap.get(date) + value;
    }

}
