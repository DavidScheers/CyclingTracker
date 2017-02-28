package main.PerformanceManagement;

import main.Functions.DataBasedFunction;
import main.Functions.DifferenceFunction;
import main.Functions.Function;
import main.Functions.PerformanceFunction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class PerformanceManagementTool implements PerformanceManagement {

    private Map<LocalDate, Double> tssMap;

    public PerformanceManagementTool() {
        this.tssMap = new HashMap<>();
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
        return new PerformanceFunction(getTssFunction(),42, LocalDate.now());
    }

    private DataBasedFunction getTssFunction() {
        return new DataBasedFunction(tssMap);
    }

    @Override
    public Function getFatigueFunction() {
        return new PerformanceFunction(getTssFunction(),7, LocalDate.now());
    }

    @Override
    public Function getFormFunction() {
        return new DifferenceFunction(getFitnessFunction(), getFatigueFunction());
    }

}
