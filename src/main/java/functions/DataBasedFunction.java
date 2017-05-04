package functions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataBasedFunction extends FunctionBaseImplementation {

    private Map<LocalDate, Double>  valueMap;

    public DataBasedFunction() {
        super();
        valueMap = new HashMap<>();
    }

    public DataBasedFunction(Map<LocalDate, Double> valueMap) {
        this.valueMap = new HashMap<>(valueMap);
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

    public Map<LocalDate, Double> getValueMap() {
        return new HashMap<>(valueMap);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataBasedFunction that = (DataBasedFunction) o;

        return valueMap != null ? valueMap.equals(that.valueMap) : that.valueMap == null;
    }

    @Override
    public int hashCode() {
        return valueMap != null ? valueMap.hashCode() : 0;
    }
}
