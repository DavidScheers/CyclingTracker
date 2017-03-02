import main.Functions.Function;
import main.Functions.ZeroOrderHoldFunction;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ZeroOrderHoldFunctionTest {

    private Function zeroOrderHoldFunction_WithData;
    private Function zeroOrderHoldFunction_Empty;
    private Function zeroOrderHoldFunction_OnlyOneDataPoint_10YearsAgo;
    private Map<LocalDate, Double> valueMap_WithData;
    private Map<LocalDate, Double> valueMap_Empty;
    private Map<LocalDate, Double> valueMap_OnlyOneDataPoint_10YearsAgo;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(5);
    private final LocalDate REALLY_LONG_AGO = LocalDate.now().minusYears(10);

    @Before
    public void setUp() {
        valueMap_WithData = new HashMap<>();
        valueMap_Empty = new HashMap<>();
        getData_ForMapWithData();
        valueMap_OnlyOneDataPoint_10YearsAgo = new HashMap<>();
        getData_ForMapWithOnlyOneDataPoint_10YearsAgo();
        zeroOrderHoldFunction_WithData = new ZeroOrderHoldFunction(valueMap_WithData);
        zeroOrderHoldFunction_Empty = new ZeroOrderHoldFunction(valueMap_Empty);
        zeroOrderHoldFunction_OnlyOneDataPoint_10YearsAgo = new ZeroOrderHoldFunction(valueMap_OnlyOneDataPoint_10YearsAgo);
    }

    private void getData_ForMapWithOnlyOneDataPoint_10YearsAgo() {
        valueMap_OnlyOneDataPoint_10YearsAgo.put(REALLY_LONG_AGO, 0.8);
    }

    private void getData_ForMapWithData() {
        valueMap_WithData.put(LONG_AGO, 0.55);
        valueMap_WithData.put(ONE_WEEK_AGO, 0.35);
        valueMap_WithData.put(YESTERYESTERDAY, 0.8);
        valueMap_WithData.put(TODAY, 0.65);
    }

    @Test
    public void EmptyMap_ShouldReturnZero() throws Exception {
        assertFunctionValueAtDay(zeroOrderHoldFunction_Empty, TODAY, 0);
    }

    @Test
    public void MapWithData_ShouldReturnValue_WhenDateIsPresent_MostRecentDate() throws Exception {
        assertFunctionValueAtDay(zeroOrderHoldFunction_WithData, TODAY, 0.65);
    }

    @Test
    public void MapWithData_ShouldReturnValue_WhenDateIsPresent_DateInPast() throws Exception {
        assertFunctionValueAtDay(zeroOrderHoldFunction_WithData, ONE_WEEK_AGO, 0.35);
    }

    @Test
    public void MapWithData_ShouldReturnFirstExistingValue_WhenAskedForNotPresentDay() throws Exception {
        assertFunctionValueAtDay(zeroOrderHoldFunction_WithData, YESTERDAY, 0.8);
    }

    @Test
    public void MapWithData_ShouldReturnZero_WhenAskedForDateBeforeFirstDate() throws Exception {
        assertFunctionValueAtDay(zeroOrderHoldFunction_WithData, LONG_AGO.minusDays(5), 0.0);
    }

    @Test
    public void MapWithOnlyOneDataPoint_10YearsAgo_ShouldReturnPreviousValue_InNoTime() throws Exception {
        assertFunctionValueAtDay(zeroOrderHoldFunction_OnlyOneDataPoint_10YearsAgo, REALLY_LONG_AGO, 0.8);
    }

    private void assertFunctionValueAtDay(Function f, LocalDate date, double value) {
        assertTrue(f.getValue(date) == value);
    }

}