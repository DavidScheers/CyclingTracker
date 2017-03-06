package test;

import main.Functions.DataBasedFunction;
import main.Functions.DifferenceFunction;
import main.Functions.Function;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by davids on 28/02/2017.
 */
public class DifferenceFunctionTest {

    private Function differenceFunction;
    private DataBasedFunction firstFunction;
    private DataBasedFunction secondFunction;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(7);

    @Before
    public void setUp() {
        firstFunction = new DataBasedFunction();
        secondFunction = new DataBasedFunction();
        differenceFunction = new DifferenceFunction(firstFunction, secondFunction);
        getDataForFirstFunction();
        getDataForSecondFunction();
    }

    private void getDataForFirstFunction() {
        firstFunction.addValue(TODAY, 5.0);
        firstFunction.addValue(YESTERDAY, 6.0);
        firstFunction.addValue(ONE_WEEK_AGO, 10.0);
        firstFunction.addValue(LONG_AGO, 20.0);
    }

    private void getDataForSecondFunction() {
        secondFunction.addValue(TODAY, 10.0);
        secondFunction.addValue(YESTERDAY, 2.0);
        secondFunction.addValue(ONE_WEEK_AGO, 6.0);
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
        assertEquals(differenceFunction.getValue(ONE_WEEK_AGO), 4.0, 0.0001);
    }

    @Test
    public void getValue_OnDayPresentInFirstNotInSecond_ShouldReturnValueOfFirst() throws Exception {
        assertEquals(differenceFunction.getValue(LONG_AGO), 20, 0.0001);
    }

}