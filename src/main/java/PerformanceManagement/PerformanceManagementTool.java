package performancemanagement;

import functions.*;

import java.time.LocalDate;

public class PerformanceManagementTool implements PerformanceManagement {

    private DataBasedFunction tssFunction;
    private ZeroOrderHoldFunction ftpFunction;
    private LocalDate startOfTrackingDate;

    public PerformanceManagementTool(LocalDate startOfTrackingDate) {
        this.startOfTrackingDate = startOfTrackingDate;
        tssFunction = new DataBasedFunction();
        ftpFunction = new ZeroOrderHoldFunction();
    }

    @Override
    public void addTrainingsDay(LocalDate date, WorkoutIntensity intensity) {
        tssFunction.addValue(date, getTrainingStressScore(date, intensity));
    }

    @Override
    public void addNewFtpMeasurement(LocalDate date, double ftp) {
        ftpFunction.addValue(date, ftp);
    }

    @Override
    public Function getFtpFunction() {
        return ftpFunction;
    }

    @Override
    public Function getFitnessFunction() {
        return new PerformanceFunction(getTssFunction(),42, startOfTrackingDate);
    }

    private DataBasedFunction getTssFunction() {
        return tssFunction;
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

    private double getTrainingStressScore(LocalDate date, WorkoutIntensity intensity) {
        return intensity.calculateTrainingStressScore(getFtpFunction().getValue(date));
    }

}