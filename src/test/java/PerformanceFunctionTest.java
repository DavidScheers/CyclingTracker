import Functions.DataBasedFunction;
import Functions.Function;
import Functions.PerformanceFunction;
import Listener.FunctionListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;


public class PerformanceFunctionTest {

    private PerformanceFunction performanceFunction;
    private DataBasedFunction tssFunction;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(5);

    @Before
    public void setUp() throws Exception {
        tssFunction = new DataBasedFunction();
        getData();
        performanceFunction = new PerformanceFunction(tssFunction, 42, LONG_AGO);
    }

    private void getData() {
        tssFunction.addValue(TODAY, 0.55);
        tssFunction.addValue(YESTERYESTERDAY, 0.4);
        tssFunction.addValue(YESTERDAY, 0.8);
        tssFunction.addValue(ONE_WEEK_AGO, 0.35);
    }

    @Test
    public void getValue_Today_ShouldReturn() throws Exception {
        assertEquals(performanceFunction.getValue(TODAY), 0.04726, 0.00001);
    }

    @Test
    public void getValue_StartDateBeforeFirstTraining() throws Exception {
        assertEquals(performanceFunction.getValue(LONG_AGO.minusDays(1)), 0.0, 0.0001);
    }

    @Test
    public void subscribe_afterRegisteringListenerOnTssFUnction_ListenerGetsCalledWhenChangeIsDetected() throws Exception {
        FunctionListener mockedListener = Mockito.mock(FunctionListener.class);
        performanceFunction.addListener(mockedListener);
        performanceFunction.changeDetected();
        verify(mockedListener).changeDetected();
    }

}