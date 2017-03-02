package main.Functions;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by davids on 2/03/2017.
 */
public class ZeroOrderHoldFunction implements Function {

    private Map<LocalDate, Double> valueMap;
    private LocalDate startDate;

    public ZeroOrderHoldFunction(Map<LocalDate, Double> valueMap, LocalDate startDate) {
        this.valueMap = valueMap;
        this.startDate = startDate;
    }

    @Override
    public double getValue(LocalDate date) {
        if (date.isBefore(startDate) || valueMap.isEmpty()) {
            return 0.0;
        } else {
            return valueMap.containsKey(date) ? valueMap.get(date) : getValue(date.minusDays(1));
        }
    }
}

/*
Maak een nieuwe implementatie van een Function die ook data-based is,
maar die als ge de waarde opvraagt op een dag waar hij geen data heeft,
op zoek gaat naar de vorige gekende waarde en die teruggeeft
 */
