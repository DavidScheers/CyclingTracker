import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import Functions.DataBasedFunction;
import Functions.DifferenceFunction;
import Functions.Function;
import Listener.FunctionListener;
import com.sun.deploy.ui.DeployEmbeddedFrameIf;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;


public class DifferenceFunctionTest {

    private DifferenceFunction differenceFunction;
    private DifferenceFunction differenceFunctionForListenerTest;
    private DataBasedFunction firstFunction;
    private DataBasedFunction secondFunction;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(7);

    @Before
    public void setUp() {
        DataBasedFunction mockedDBOne = Mockito.mock(DataBasedFunction.class);
        when(mockedDBOne.getValue(YESTERDAY)).thenReturn(10.0);
        DataBasedFunction mockedDBTwo = Mockito.mock(DataBasedFunction.class);
        when(mockedDBOne.getValue(YESTERDAY)).thenReturn(5.0);
        differenceFunctionForListenerTest = new DifferenceFunction(mockedDBOne, mockedDBTwo);
        firstFunction = new DataBasedFunction();
        secondFunction = new DataBasedFunction();
        getDataForFirstFunction();
        getDataForSecondFunction();
        differenceFunction = new DifferenceFunction(firstFunction, secondFunction);
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

    @Test
    public void afterRegisteringListenerOnFirstFunction_ListenerGetsCalledWhenChangeIsDetected() throws Exception {
        FunctionListener mockedListener = Mockito.mock(FunctionListener.class);
        differenceFunctionForListenerTest.addListener(mockedListener);
        differenceFunctionForListenerTest.changeDetected();
        verify(mockedListener).changeDetected();

    }
}