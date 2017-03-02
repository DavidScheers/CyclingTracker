package main.PerformanceManagement;

import java.time.Duration;

/**
 * Created by davids on 2/03/2017.
 */
public class WorkoutIntensity {

    private Duration workoutDuration;
    private double power;

    public WorkoutIntensity(Duration workoutDuration, double power) {
        this.workoutDuration = workoutDuration;
        power = power;
    }

    public Duration getWorkoutDuration() {
        return workoutDuration;
    }

    public double getPower() {
        return power;
    }
}
