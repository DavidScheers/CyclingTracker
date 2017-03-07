import Functions.*;
import Listener.FunctionListener;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShiftedFunctionTest {

    private Function shiftedFunction;
    private DifferenceFunction functionToShift;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(7);

    @Before
    public void setUp() {
        functionToShift = new DifferenceFunction(one, two);
        shiftedFunction = new ShiftedFunction(functionToShift, 1);
    }

    @Test
    public void getValueToday_ShouldBySameAs_FunctionToShiftYesterday_IfDatesToShiftIsOne() throws Exception {
        Assertions.assertThat(shiftedFunction.getValue(TODAY)).isEqualTo(functionToShift.getValue(YESTERDAY));
    }
    /*
    @Test
    public void afterRegisteringListenerOnFirstFunction_ListenerGetsCalledWhenChangeIsDetected() throws Exception {
        FunctionListener mockedFunctionListener = Mockito.mock(FunctionListener.class);
        functionToShift.addListener(mockedFunctionListener);
        verify(mockedFunctionListener).changeDetected();
    }
    */
}