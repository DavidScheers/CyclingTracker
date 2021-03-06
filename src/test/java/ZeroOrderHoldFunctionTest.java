import functions.Function;
import functions.ZeroOrderHoldFunction;
import listener.FunctionListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class ZeroOrderHoldFunctionTest {

    private ZeroOrderHoldFunction zeroOrderHoldFunction_WithData;
    private ZeroOrderHoldFunction zeroOrderHoldFunction_Empty;
    private ZeroOrderHoldFunction zeroOrderHoldFunction_OnlyOneDataPoint_10YearsAgo;
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private static final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private static final LocalDate LONG_AGO = LocalDate.now().minusMonths(5);
    private static final LocalDate REALLY_LONG_AGO = LocalDate.now().minusYears(10);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private FunctionListener functionListener;


    @Before
    public void setUp() {
        zeroOrderHoldFunction_WithData = new ZeroOrderHoldFunction();
        zeroOrderHoldFunction_Empty = new ZeroOrderHoldFunction();
        zeroOrderHoldFunction_OnlyOneDataPoint_10YearsAgo = new ZeroOrderHoldFunction();
        getData_ForMapWithData();
        getData_ForMapWithOnlyOneDataPoint_10YearsAgo();
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

    @Test
    public void afterRegisteringListener_ListenersGetsCalledWhenZeroOrderHoldFunctionChanges() throws Exception {
        zeroOrderHoldFunction_WithData.addListener(functionListener);
        zeroOrderHoldFunction_WithData.addValue(TODAY, 0.55);
        verify(functionListener).changeDetected();
    }

    private void assertFunctionValueAtDay(Function f, LocalDate date, double value) {
        assertTrue(f.getValue(date) == value);
    }

    private void getData_ForMapWithOnlyOneDataPoint_10YearsAgo() {
        zeroOrderHoldFunction_OnlyOneDataPoint_10YearsAgo.addValue(REALLY_LONG_AGO, 0.8);
    }

    private void getData_ForMapWithData() {
        zeroOrderHoldFunction_WithData.addValue(LONG_AGO, 0.55);
        zeroOrderHoldFunction_WithData.addValue(ONE_WEEK_AGO, 0.35);
        zeroOrderHoldFunction_WithData.addValue(YESTERYESTERDAY, 0.8);
        zeroOrderHoldFunction_WithData.addValue(TODAY, 0.65);
    }

}