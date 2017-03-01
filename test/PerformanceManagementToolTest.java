
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
        assertTrue(detectChange(fatigueFunction, yesterYesterDay, yesterDay) == 1);
    }

    @Test
    public void AddTrainingsDay_FitnessRoseNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.5);
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertTrue(detectChange(fitnessFunction, yesterYesterDay, yesterDay) == 1);
    }

    @Test
    public void AddTrainingsDay_FormDropsNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(yesterDay, 0.5);
        Function formFunction = performanceManagementTool.getFormFunction();
        assertTrue(detectChange(formFunction, yesterYesterDay, today) == -1);
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

    private boolean valueRose(Function function, LocalDate start, LocalDate end) {
        return function.getValue(end) > function.getValue(start);
    }

    private boolean valueDropped(Function function, LocalDate start, LocalDate end) {
        return function.getValue(end) < function.getValue(start);
    }

    private int detectChange(Function function, LocalDate start, LocalDate end) {
        int change;
        if (valueRose(function, start, end)) {
            change = 1;
        } else if (valueDropped(function, start, end)) {
            change = -1;
        } else {
            change = 0;
        }
        return change;
    }
}
