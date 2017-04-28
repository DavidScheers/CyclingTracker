import functions.*;
import listener.FunctionListener;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShiftedFunctionTest {

    private ShiftedFunction shiftedFunction;
    private DifferenceFunction functionToShift;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(7);

    @Before
    public void setUp() {
        PerformanceFunction mockedPerfFuncOne = Mockito.mock(PerformanceFunction.class);
        when(mockedPerfFuncOne.getValue(YESTERDAY)).thenReturn(5.0);
        PerformanceFunction mockedPerfFuncOTwo = Mockito.mock(PerformanceFunction.class);
        when(mockedPerfFuncOTwo.getValue(YESTERDAY)).thenReturn(2.0);
        functionToShift = new DifferenceFunction(mockedPerfFuncOne, mockedPerfFuncOTwo);
        shiftedFunction = new ShiftedFunction(functionToShift, 1);
    }

    @Test
    public void getValueToday_ShouldBySameAs_FunctionToShiftYesterday_IfDatesToShiftIsOne() throws Exception {
        Assertions.assertThat(shiftedFunction.getValue(TODAY)).isEqualTo(functionToShift.getValue(YESTERDAY));
    }

    @Test
    public void afterRegisteringListenerOnDiffFunction_ListenerGetsCalledWhenChangeIsDetectedInDiffFunction() throws Exception {
        FunctionListener mockedListener = Mockito.mock(FunctionListener.class);
        shiftedFunction.addListener(mockedListener);
        shiftedFunction.changeDetected();
        verify(mockedListener).changeDetected();
    }

}