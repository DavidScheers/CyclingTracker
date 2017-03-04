package main.PerformanceManagement;

import java.time.Duration;

/**
 * Created by davids on 2/03/2017.
 */
public class WorkoutIntensity {

    private int workoutDurationInMinutes;
    private double power;

    public WorkoutIntensity(int workoutDurationInMinutes, double power) {
        this.workoutDurationInMinutes = workoutDurationInMinutes;
        power = power;
    }

    public int getWorkoutDuration() {
        return workoutDurationInMinutes;
    }

    public double getPower() {
        return power;
    }

    public double calculateTrainingStressScore(double ftp) {
        return (workoutDurationInMinutes*60*power*(power/ftp)) / (ftp*360000);
    }
}
