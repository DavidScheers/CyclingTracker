package main.PerformanceManagement;

import main.Functions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PerformanceManagementTool implements PerformanceManagement {

    private Map<LocalDate, Double> tssMap;
    private Map<LocalDate, Double> ftpMap;
    private LocalDate startOfTrackingDate;

    public PerformanceManagementTool(LocalDate startOfTrackingDate) {
        this.tssMap = new HashMap<>();
        this.startOfTrackingDate = startOfTrackingDate;
        this.ftpMap = new HashMap<>();
    }

    @Override
    public void addTrainingsDay(LocalDate date, WorkoutIntensity intensity) {
        if (tssMap.containsKey(date)) {
            tssMap.put(date, getUpdatedTss(date, intensity));
        } else {
            tssMap.put(date, getTrainingStressScore(date, intensity));
        }
    }

    @Override
    public void addNewFtpMeasurement(LocalDate date, double ftp) {
        ftpMap.put(date, ftp);
    }

    @Override
    public Function getFtpFunction() {
        return new ZeroOrderHoldFunction(ftpMap);
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

    private double getUpdatedTss(LocalDate date, WorkoutIntensity intensity) {
        return tssMap.get(date) + getTrainingStressScore(date, intensity);
    }

    private double getTrainingStressScore(LocalDate date, WorkoutIntensity intensity) {
        return intensity.calculateTrainingStressScore(getFtpFunction().getValue(date));
    }
}