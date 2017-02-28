package main;

import main.Functions.DataBasedFunction;
import main.Functions.FormFunction;
import main.Functions.Function;
import main.Functions.PerformanceFunction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by davids on 28/02/2017.
 */
public class PerformanceManagementTool implements PerformanceManagement {


    private Map<LocalDate, Double> tssMap;
    private Function tssFunction;

    public PerformanceManagementTool() {
        this.tssMap = new HashMap<>();
        this.tssFunction = new DataBasedFunction(tssMap);
    }

    @Override
    public void addTrainingsDay(LocalDate date, double tss) {
        tssMap.put(date, tss);
    }

    @Override
    public Function getFitnessFunction() {
        Function fitnessFunction = new PerformanceFunction(tssFunction,42, LocalDate.now());
        return fitnessFunction;
    }

    @Override
    public Function getFatigueFunction() {
        Function fatigueFunction = new PerformanceFunction(tssFunction,7, LocalDate.now());
        return fatigueFunction;
    }

    @Override
    public Function getFormFunction() {
        Function formFunction = new FormFunction(getFitnessFunction(), getFatigueFunction());
        return formFunction;
    }
}
