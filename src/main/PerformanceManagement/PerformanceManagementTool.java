package main.PerformanceManagement;

import main.Functions.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PerformanceManagementTool implements PerformanceManagement {

    private Map<LocalDate, Double> tssMap;
    private LocalDate startOfTrackingDate;

    public PerformanceManagementTool(LocalDate startOfTrackingDate) {
        this.tssMap = new HashMap<>();
        this.startOfTrackingDate = startOfTrackingDate;
    }

    @Override
    public void addTrainingsDay(LocalDate date, double tss) {
        if (tssMap.containsKey(date)) {
            double temp = tssMap.get(date);
            tssMap.put(date, temp+tss);
        } else {
            tssMap.put(date, tss);
        }
    }

    @Override
    public Function getFitnessFunction() {
        return new PerformanceFunction(getTssFunction(),42, startOfTrackingDate);
    }

    private DataBasedFunction getTssFunction() {
        return new DataBasedFunction(tssMap);
    }

    @Override
    public Function getFatigueFunction() {
        return new PerformanceFunction(getTssFunction(),7, startOfTrackingDate);
    }

    @Override
    public Function getFormFunction() {
        DifferenceFunction differenceFunction = new DifferenceFunction(getFitnessFunction(), getFatigueFunction());
        return new ShiftedFunction(differenceFunction, 1);
    }

}
