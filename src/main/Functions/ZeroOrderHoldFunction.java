package main.Functions;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by davids on 2/03/2017.
 */
public class ZeroOrderHoldFunction implements Function {

    private Map<LocalDate, Double> valueMap;

    public ZeroOrderHoldFunction(Map<LocalDate, Double> valueMap) {
        this.valueMap = valueMap;
    }

    @Override
    public double getValue(LocalDate date) {
        return valueMap.containsKey(date) ? valueMap.get(date) : getValue(date.minusDays(1));
    }
}

/*
Maak een nieuwe implementatie van een Function die ook data-based is,
maar die als ge de waarde opvraagt op een dag waar hij geen data heeft,
op zoek gaat naar de vorige gekende waarde en die teruggeeft
 */