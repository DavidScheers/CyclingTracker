package performancemanagement;

public class WorkoutIntensity {

    private int workoutDurationInMinutes;
    private double power;

    public WorkoutIntensity(int workoutDurationInMinutes, double power) {
        this.workoutDurationInMinutes = workoutDurationInMinutes;
        this.power = power;
    }

    double calculateTrainingStressScore(double ftp) {
        return (workoutDurationInMinutes * 60 * power * (power / ftp)) / (ftp * 360000);
    }
}
