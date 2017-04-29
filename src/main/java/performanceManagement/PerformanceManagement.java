package performanceManagement;

import functions.Function;
import java.time.LocalDate;

public interface PerformanceManagement {

    void addTrainingsDay(LocalDate date, WorkoutIntensity intensity);
    void addNewFtpMeasurement(LocalDate date, double ftp);

    Function getFtpFunction();
    Function getFitnessFunction();
    Function getFatigueFunction();

    Function getFormFunction();
}

