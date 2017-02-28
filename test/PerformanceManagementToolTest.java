
import main.PerformanceManagementTool;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by davids on 28/02/2017.
 */
public class PerformanceManagementToolTest {

    private PerformanceManagementTool performanceManagementTool;

    @Before
    public void setUp() throws Exception {
        performanceManagementTool = new PerformanceManagementTool();
        /*
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(3), 0.45);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(2), 0.60);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.85);
        */
    }

    @Test
    public void addTrainingsDay_AddaDay_Unique() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.85);
        assertTrue(performanceManagementTool.getTssMap().containsKey(LocalDate.now().minusDays(1)));
    }

    @Test
    public void addTrainingsDay_AddaDay_Existing() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.85);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.05);
        assertTrue(performanceManagementTool.getTssMap().containsKey(LocalDate.now().minusDays(1)));
    }

    @Test
    public void addTrainingsDay_AddaSecondDay_Unique() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.85);
        performanceManagementTool.addTrainingsDay(LocalDate.now(), 0.5);
        assertTrue(performanceManagementTool.getTssMap().containsKey(LocalDate.now().minusDays(1))
        && performanceManagementTool.getTssMap().containsKey(LocalDate.now()));
    }

    @Test
    public void getFitnessFunction() throws Exception {

    }

    @Test
    public void getFatigueFunction() throws Exception {

    }

    @Test
    public void getFormFunction() throws Exception {

    }

}