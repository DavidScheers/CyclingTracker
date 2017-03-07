import Functions.*;
import Listener.FunctionListener;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class ShiftedFunctionTest {

    private Function shiftedFunction;
    private DifferenceFunction functionToShift;
    private DataBasedFunction tssFunction;
    private PerformanceFunction one;
    private PerformanceFunction two;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(7);

    @Before
    public void setUp() {
        tssFunction = new DataBasedFunction();
        getData();
        one = new PerformanceFunction(tssFunction, 42, LONG_AGO);
        two = new PerformanceFunction(tssFunction, 7, LONG_AGO);
        functionToShift = new DifferenceFunction(one, two);
        shiftedFunction = new ShiftedFunction(functionToShift, 1);
    }

    private void getData() {
        tssFunction.addValue(TODAY, 0.55);
        tssFunction.addValue(YESTERYESTERDAY, 0.4);
        tssFunction.addValue(YESTERDAY, 0.8);
        tssFunction.addValue(ONE_WEEK_AGO, 0.35);
    }

    @Test
    public void getValueToday_ShouldBySameAs_FunctionToShiftYesterday_IfDatesToShiftIsOne() throws Exception {
        Assertions.assertThat(shiftedFunction.getValue(TODAY)).isEqualTo(functionToShift.getValue(YESTERDAY));
    }

    @Test
    public void afterRegisteringListenerOnFirstFunction_ListenerGetsCalledWhenChangeIsDetected() throws Exception {
        FunctionListener mockedFunctionListener = Mockito.mock(FunctionListener.class);
        FunctionListener mockedFunctionListenerForFunctionToShift = Mockito.mock(FunctionListener.class);
        FunctionListener mockedFunctionListenerForTss = Mockito.mock(FunctionListener.class);
        functionToShift.addListener(mockedFunctionListener);
        one.addListener(mockedFunctionListenerForFunctionToShift);
        tssFunction.addListener(mockedFunctionListenerForTss);
        tssFunction.addValue(TODAY, 0.55);
        verify(mockedFunctionListener).changeDetected();
    }
}