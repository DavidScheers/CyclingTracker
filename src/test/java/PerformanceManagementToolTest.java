import static org.junit.Assert.*;

import functions.Function;
import performancemanagement.PerformanceManagementTool;
import performancemanagement.WorkoutIntensity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;


public class PerformanceManagementToolTest {

    private PerformanceManagementTool performanceManagementTool;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(7);

    @Before
    public void setUp() throws Exception {
        performanceManagementTool = new PerformanceManagementTool(LONG_AGO);
        performanceManagementTool.addNewFtpMeasurement(LONG_AGO, 230.0);
    }

    @Test
    public void BeforeAddingTrainingDays_FitnessShouldBeZero() throws Exception {
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertTrue(fitnessFunction.getValue(TODAY) == 0);
    }

    @Test
    public void BeforeAddingTrainingDays_FormShouldBeZero() throws Exception {
        Function formFunction = performanceManagementTool.getFormFunction();
        assertTrue(formFunction.getValue(TODAY) == 0);
    }

    @Test
    public void BeforeAddingTrainingDays_FatigueShouldBeZero() throws Exception {
        Function fatigueFunction = performanceManagementTool.getFatigueFunction();
        assertTrue(fatigueFunction.getValue(TODAY) == 0);
    }

    @Test
    public void AddTrainingsDay_FatigueRoseNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), createWorkoutIntensity(30, 220));
        Function fatigueFunction = performanceManagementTool.getFatigueFunction();
        assertRisingFunction(fatigueFunction, YESTERDAY);
    }

    @Test
    public void AddTrainingsDay_FitnessRoseNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), createWorkoutIntensity(30, 220));
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertRisingFunction(fitnessFunction, YESTERDAY);
    }

    @Test
    public void AddTrainingsDay_FormDropsNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(YESTERYESTERDAY, createWorkoutIntensity(30, 220));
        Function formFunction = performanceManagementTool.getFormFunction();
        assertRisingFunction(formFunction, TODAY);
    }

    @Test
    public void AddTrainingsDay_FitnessDayBeforeIsUnaffected() throws Exception {
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        double fitnessBefore = fitnessFunction.getValue(YESTERYESTERDAY);
        performanceManagementTool.addTrainingsDay(YESTERDAY, createWorkoutIntensity(30, 220));
        double fitnessBefore_AfterAddingDay = fitnessFunction.getValue(YESTERYESTERDAY);
        assertTrue(fitnessBefore == fitnessBefore_AfterAddingDay);
    }

    @Test
    public void AfterRestDays_FormShouldIncrease() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(5), createWorkoutIntensity(30, 220));
        Function formFunction = performanceManagementTool.getFormFunction();
        double formAfterTraining = formFunction.getValue(LocalDate.now().minusDays(4));
        double formAfterRest = formFunction.getValue(YESTERYESTERDAY);

        assertTrue(formAfterTraining < formAfterRest);
    }

    @Test
    public void HarderTraining_ShouldResultInBiggerFatigueRise() throws Exception {
        performanceManagementTool.addTrainingsDay(TODAY, createWorkoutIntensity(5, 100));
        PerformanceManagementTool performanceManagementToolHard = new PerformanceManagementTool(LONG_AGO);
        performanceManagementToolHard.addTrainingsDay(TODAY, createWorkoutIntensity(30, 220));

        Function fatigueFunction_Soft = performanceManagementTool.getFatigueFunction();
        Function fatigueFunction_Hard = performanceManagementToolHard.getFatigueFunction();

        double fatigueRise_SoftTraining = calculateRise(fatigueFunction_Soft);
        double fatigueRise_HardTraining = calculateRise(fatigueFunction_Hard);

        assertTrue(fatigueRise_SoftTraining < fatigueRise_HardTraining);
    }

    private double calculateRise(Function fatigueFunction) {
        return fatigueFunction.getValue(TODAY) - fatigueFunction.getValue(YESTERDAY);
    }

    @Test
    public void FitnessDecreasesIfYouDontTrain() throws Exception {
        performanceManagementTool.addTrainingsDay(ONE_WEEK_AGO, createWorkoutIntensity(50, 220));
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertRisingFunction(fitnessFunction, ONE_WEEK_AGO);
        assertDecreasingFunction(fitnessFunction, YESTERDAY);
    }

    private void assertRisingFunction(Function function, LocalDate date) {
        assertTrue(function.getValue(date) > function.getValue(date.minusDays(1)));
    }

    private void assertDecreasingFunction(Function function, LocalDate date) {
        assertTrue(function.getValue(date) < function.getValue(date.minusDays(1)));
    }

    private WorkoutIntensity createWorkoutIntensity(int duration, double power) {
        return new WorkoutIntensity(duration, power);
    }
}
