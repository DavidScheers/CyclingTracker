package main.PerformanceManagement;

import main.Functions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PerformanceManagementTool implements PerformanceManagement {

    private Map<LocalDate, WorkoutIntensity> tssMap;
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
            tssMap.put(date, getWorkoutIntensity(date, intensity));
        } else {
            tssMap.put(date, intensity);
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

    private WorkoutIntensity getWorkoutIntensity(LocalDate date, WorkoutIntensity intensity) {
        return new WorkoutIntensity(getNewDuration(date, intensity), getNewPower(date, intensity));
    }

    private double getNewPower(LocalDate date, WorkoutIntensity intensity) {
        return getPower(date) + intensity.getPower();
    }

    private Duration getNewDuration(LocalDate date, WorkoutIntensity intensity) {
        return getWorkoutDuration(date).plus(intensity.getWorkoutDuration());
    }

    private double getPower(LocalDate date) {
        return tssMap.get(date).getPower();
    }

    private Duration getWorkoutDuration(LocalDate date) {
        return tssMap.get(date).getWorkoutDuration();
    }

}

/*
    @Override
    public void addTrainingsDay(LocalDate date, double tss) {
        if (tssMap.containsKey(date)) {
            double temp = tssMap.get(date);
            tssMap.put(date, temp+tss);
        } else {
            tssMap.put(date, tss);
        }
    }
 */
