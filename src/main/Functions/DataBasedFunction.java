package main.Functions;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by davids on 27/02/2017.
 */
public class DataBasedFunction implements Function {

    private Map<LocalDate, Double>  valueMap;

    public DataBasedFunction(Map<LocalDate, Double> valueMap) {
        this.valueMap = valueMap;
    }

    @Override
    public double getValue(LocalDate inputDate) {
        double output;
        if (valueMap.containsKey(inputDate)) {
            output = valueMap.get(inputDate);
        } else {
            output = 0;
        }
        return output;
    }
}
