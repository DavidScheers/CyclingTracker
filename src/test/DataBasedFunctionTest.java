package test;

import main.Functions.DataBasedFunction;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DataBasedFunctionTest {

    private DataBasedFunction dataBasedFunction;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);

    @Before
    public void setUp() {
        dataBasedFunction = new DataBasedFunction();
        getData();

    }

    private void getData() {
        dataBasedFunction.addValue(TODAY, 5.0);
        dataBasedFunction.addValue(YESTERDAY, 6.0);
        dataBasedFunction.addValue(ONE_WEEK_AGO, 10.0);
    }

    @Test
    public void getValue_ForToday_shouldReturnFive() throws Exception {
        assertEquals(dataBasedFunction.getValue(TODAY), 5.0, 0.0001);
    }

    @Test
    public void getValue_ForYesterday_ShouldReturnSix() throws Exception {
        assertEquals(dataBasedFunction.getValue(YESTERDAY), 6.0, 0.0001);
    }

    @Test
    public void getValue_ForNotExistingDay_ShouldReturnZero() throws Exception {
        assertEquals(dataBasedFunction.getValue(ONE_WEEK_AGO.plusDays(1)), 0.0, 0.0001);
    }


}