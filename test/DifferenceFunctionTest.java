import main.Functions.DataBasedFunction;
import main.Functions.DifferenceFunction;
import main.Functions.Function;
import main.Functions.PerformanceFunction;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by davids on 28/02/2017.
 */
public class DifferenceFunctionTest {

    private Function differenceFunction;
    private Map<LocalDate, Double> valueMapFirst;
    private Map<LocalDate, Double> valueMapSecond;
    private Function firstFunction;
    private Function secondFunction;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(7);

    @Before
    public void setUp() {
        valueMapFirst = new HashMap<>();
        valueMapSecond = new HashMap<>();
        getDataForFirstFunction();
        getDataForSecondFunction();
        firstFunction = new DataBasedFunction(valueMapFirst);
        secondFunction = new DataBasedFunction(valueMapSecond);
        differenceFunction = new DifferenceFunction(firstFunction, secondFunction);
    }

    private void getDataForFirstFunction() {
        valueMapFirst.put(TODAY, 5.0);
        valueMapFirst.put(YESTERDAY, 6.0);
        valueMapFirst.put(ONE_WEEK_AGO, 10.0);
        valueMapFirst.put(LONG_AGO, 20.0);
    }

    private void getDataForSecondFunction() {
        valueMapSecond.put(TODAY, 10.0);
        valueMapSecond.put(YESTERDAY, 2.0);
        valueMapSecond.put(ONE_WEEK_AGO, 6.0);
    }

    @Test
    public void getValue_OnDateWhichDoesntExistInBothFunctions_ShouldReturnZero() throws Exception {
        assertEquals(differenceFunction.getValue(LONG_AGO.plusDays(1)), 0.0, 0.0001);
    }

    @Test
    public void getValue_OnToday_ShouldReturnMinusFive() throws Exception {
        assertEquals(differenceFunction.getValue(TODAY), -5.0, 0.0001);
    }

    @Test
    public void getValue_OnOneWeekAgo_ShouldReturnEigth() throws Exception {
        assertEquals(differenceFunction.getValue(TODAY), 8.0, 0.0001);
    }

    @Test
    public void getValue_OnDayPresentInFirstNotInSecond_ShouldReturnMinusFive() throws Exception {
        //assertEquals();
    }

}