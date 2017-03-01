
import main.Functions.Function;
import main.PerformanceManagement.PerformanceManagementTool;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;


public class PerformanceManagementToolTest {

    private PerformanceManagementTool performanceManagementTool;

    @Before
    public void setUp() throws Exception {
        performanceManagementTool = new PerformanceManagementTool(LocalDate.now().minusMonths(3));
    }

    @Test
    public void BeforeAddingTrainingDays_FitnessShouldBeZero() throws Exception {
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertTrue(fitnessFunction.getValue(LocalDate.now()) == 0);
    }

    @Test
    public void BeforeAddingTrainingDays_FormShouldBeZero() throws Exception {
        Function formFunction = performanceManagementTool.getFormFunction();
        assertTrue(formFunction.getValue(LocalDate.now()) == 0);
    }

    @Test
    public void BeforeAddingTrainingDays_FatigueShouldBeZero() throws Exception {
        Function fatigueFunction = performanceManagementTool.getFatigueFunction();
        assertTrue(fatigueFunction.getValue(LocalDate.now()) == 0);
    }

    @Test
    public void AddTrainingsDay_FatigueRoseNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.5);
        Function fatigueFunction = performanceManagementTool.getFatigueFunction();
        assertTrue(fatigueFunction.getValue(LocalDate.now().minusDays(1)) > fatigueFunction.getValue(LocalDate.now().minusDays(2)));
    }

    @Test
    public void AddTrainingsDay_FitnessRoseNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.5);
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        assertTrue(fitnessFunction.getValue(LocalDate.now().minusDays(1)) > fitnessFunction.getValue(LocalDate.now().minusDays(2)));
    }

    @Test
    public void AddTrainingsDay_FormDropsNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.5);
        Function formFunction = performanceManagementTool.getFormFunction();
        assertTrue(formFunction.getValue(LocalDate.now()) < formFunction.getValue(LocalDate.now().minusDays(1)));
    }

    @Test
    public void AddTrainingsDay_FitnessDayBeforeIsUnaffected() throws Exception {
        Function fitnessFunction = performanceManagementTool.getFitnessFunction();
        double fitnessBefore = fitnessFunction.getValue(LocalDate.now().minusDays(2));
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.5);
        double fitnessBefore_AfterAddingDay = fitnessFunction.getValue(LocalDate.now().minusDays(2));
        assertTrue(fitnessBefore == fitnessBefore_AfterAddingDay);
    }

    @Test
    public void AfterRestDays_FormShouldIncrease() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(5), 0.8);
        Function formFunction = performanceManagementTool.getFormFunction();
        double formAfterTraining = formFunction.getValue(LocalDate.now().minusDays(4));
        double formAfterRest = formFunction.getValue(LocalDate.now().minusDays(2));

        assertTrue(formAfterTraining < formAfterRest);
    }

    @Test
    public void HarderTraining_ResultsInBiggerFatigueRise() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(5), 0.05);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.95);
        Function fatigueFunction = performanceManagementTool.getFatigueFunction();
        double fatigueRise_SoftTraining = fatigueFunction.getValue(LocalDate.now().minusDays(5)) - fatigueFunction.getValue(LocalDate.now().minusDays(6));
        double fatigueRise_HardTraining = fatigueFunction.getValue(LocalDate.now().minusDays(1)) - fatigueFunction.getValue(LocalDate.now().minusDays(2));
        assertTrue(fatigueRise_SoftTraining < fatigueRise_HardTraining);
    }
}
