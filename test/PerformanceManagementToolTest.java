
import main.Functions.Function;
import main.PerformanceManagement.PerformanceManagementTool;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;


public class PerformanceManagementToolTest {

    private PerformanceManagementTool performanceManagementTool;
    private final LocalDate today = LocalDate.now();
    private final LocalDate yesterDay = LocalDate.now().minusDays(1);
    private final LocalDate yesterYesterDay = LocalDate.now().minusDays(2);
    private final LocalDate oneWeekAgo = LocalDate.now().minusDays(7);

    @Before
    public void setUp() throws Exception {
        performanceManagementTool = new PerformanceManagementTool(LocalDate.now().minusMonths(3));
    }

    @Test
    public void BeforeAddingTrainingDays_FitnessShouldBeZero() throws Exception {
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertTrue(fitnessFunction.getValue(today) == 0);
    }

    @Test
    public void BeforeAddingTrainingDays_FormShouldBeZero() throws Exception {
        Function formFunction = performanceManagementTool.getFormFunction();
        assertTrue(formFunction.getValue(today) == 0);
    }

    @Test
    public void BeforeAddingTrainingDays_FatigueShouldBeZero() throws Exception {
        Function fatigueFunction = performanceManagementTool.getFatigueFunction();
        assertTrue(fatigueFunction.getValue(today) == 0);
    }

    @Test
    public void AddTrainingsDay_FatigueRoseNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.5);
        Function fatigueFunction = performanceManagementTool.getFatigueFunction();
        assertTrue(assertRisingFunction(fatigueFunction, yesterDay));
    }

    @Test
    public void AddTrainingsDay_FitnessRoseNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.5);
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertTrue(assertRisingFunction(fitnessFunction, yesterDay));
    }

    @Test
    public void AddTrainingsDay_FormDropsNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(yesterYesterDay, 0.5);
        Function formFunction = performanceManagementTool.getFormFunction();
        assertTrue(assertRisingFunction(formFunction, today));
    }

    @Test
    public void AddTrainingsDay_FitnessDayBeforeIsUnaffected() throws Exception {
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        double fitnessBefore = fitnessFunction.getValue(yesterYesterDay);
        performanceManagementTool.addTrainingsDay(yesterDay, 0.5);
        double fitnessBefore_AfterAddingDay = fitnessFunction.getValue(yesterYesterDay);
        assertTrue(fitnessBefore == fitnessBefore_AfterAddingDay);
    }

    @Test
    public void AfterRestDays_FormShouldIncrease() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(5), 0.8);
        Function formFunction = performanceManagementTool.getFormFunction();
        double formAfterTraining = formFunction.getValue(LocalDate.now().minusDays(4));
        double formAfterRest = formFunction.getValue(yesterYesterDay);

        assertTrue(formAfterTraining < formAfterRest);
    }

    @Test
    public void HarderTraining_ResultsInBiggerFatigueRise() throws Exception {
        performanceManagementTool.addTrainingsDay(oneWeekAgo, 0.05);
        performanceManagementTool.addTrainingsDay(yesterDay, 0.95);
        Function fatigueFunction = performanceManagementTool.getFatigueFunction();
        double fatigueRise_SoftTraining = fatigueFunction.getValue(oneWeekAgo) - fatigueFunction.getValue(LocalDate.now().minusDays(6));
        double fatigueRise_HardTraining = fatigueFunction.getValue(yesterDay) - fatigueFunction.getValue(yesterYesterDay);
        assertTrue(fatigueRise_SoftTraining < fatigueRise_HardTraining);
    }

    @Test
    public void FitnessDecreasesIfYouDontTrain() throws Exception {
        performanceManagementTool.addTrainingsDay(oneWeekAgo, 0.95);
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertTrue(assertRisingFunction(fitnessFunction, oneWeekAgo));
        assertFalse(assertRisingFunction(fitnessFunction, yesterDay));
    }


    private boolean assertRisingFunction(Function function, LocalDate date) {
        boolean assertion = false;
        if (function.getValue(date) > function.getValue(date.minusDays(1))) {
            assertion = true;
        }
        return assertion;
    }
}
