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

    @Before
    public void setUp() throws Exception {
        Map<LocalDate, Double> valueMap = new HashMap<>();
        valueMap.put(LocalDate.now().minusDays(3), 0.55);
        valueMap.put(LocalDate.now().minusDays(2), 0.4);
        valueMap.put(LocalDate.now().minusDays(1), 0.8);
        tssFunction = new DataBasedFunction(valueMap);
        performanceFunction = new PerformanceFunction(tssFunction, 42, LocalDate.now().minusDays(4));
    }

    @Test
    public void getValue() throws Exception {
        System.out.println(performanceFunction.getValue(LocalDate.now()));
        assertEquals(performanceFunction.getValue(LocalDate.now()), 0.03940194076181507, 0.00001);
    }

    @Test
    public void getValue_StartDateBeforeFirstTraining() throws Exception {
        System.out.println(performanceFunction.getValue(LocalDate.now().minusDays(4)));
        assertEquals(performanceFunction.getValue(LocalDate.now().minusDays(4)), 0.0, 0.0001);
    }

}