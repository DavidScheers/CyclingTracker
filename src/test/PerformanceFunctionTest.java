package test;

import main.Functions.DataBasedFunction;
import main.Functions.Function;
import main.Functions.PerformanceFunction;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by davids on 28/02/2017.
 */
public class PerformanceFunctionTest {

    private Function performanceFunction;
    private Function tssFunction;
    private Map<LocalDate, Double> valueMap;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(5);

    @Before
    public void setUp() throws Exception {
        valueMap = new HashMap<>();
        getData();
        tssFunction = new DataBasedFunction(valueMap);
        performanceFunction = new PerformanceFunction(tssFunction, 42, LONG_AGO);
    }

    private void getData() {
        valueMap.put(TODAY, 0.55);
        valueMap.put(YESTERYESTERDAY, 0.4);
        valueMap.put(YESTERDAY, 0.8);
        valueMap.put(ONE_WEEK_AGO, 0.35);
    }

    @Test
    public void getValue_Today_ShouldReturn() throws Exception {
        assertEquals(performanceFunction.getValue(TODAY), 0.04029, 0.00001);
    }

    @Test
    public void getValue_StartDateBeforeFirstTraining() throws Exception {
        assertEquals(performanceFunction.getValue(ONE_WEEK_AGO), 0.008234, 0.0001);
    }

}