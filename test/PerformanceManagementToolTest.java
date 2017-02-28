
import main.PerformanceManagement.PerformanceManagementTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by davids on 28/02/2017.
 */
public class PerformanceManagementToolTest {

    private PerformanceManagementTool performanceManagementTool;

    @Before
    public void setUp() throws Exception {
        performanceManagementTool = new PerformanceManagementTool();

        //performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(3), 0.45);
        //performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(2), 0.60);
        //performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.85);
    }

    @Test
    public void AddTraingignsDay_FatigueRoseNextDay() throws Exception {
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(1), 0.5);
        //Assert.
    }

    @Test
    public void getFatigueFunction() throws Exception {

    }

    @Test
    public void getFormFunction() throws Exception {

    }

}

/*
- Als ge een trainingsdag toevoegt, moet de dag erna uw fatigue gestegen zijn
- Als ge een trainingsdag toevoegt, moet de dag ervoor uw fatigue ongewijzigd blijven
- Als ge harder traint, moet uw fatigue meer stijgen
 */