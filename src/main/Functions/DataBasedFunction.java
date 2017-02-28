package main.Functions;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by davids on 27/02/2017.
 */
public class DataBasedFunction implements Function {

    private Map<LocalDate, Double>  valueMap;

    @Override
    public double getValue(LocalDate inputDate) {
        if (valueMap.containsKey(inputDate)) {
            return valueMap.get(inputDate);
        } else {
            return 0;
        }
    }

    public DataBasedFunction(Map<LocalDate, Double> valueMap) {
        this.valueMap = valueMap;
    }
}
