import functions.*;
import listener.FunctionListener;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShiftedFunctionTest {

    private static final int DATES_TO_SHIFT = 1;
    private static final double VALUE = 10.0;
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Function functionToShift;

    @Mock
    private FunctionListener functionListener;

    @InjectMocks ShiftedFunction shiftedFunction;

    @Before
    public void setUp() throws Exception {
        shiftedFunction = new ShiftedFunction(functionToShift, DATES_TO_SHIFT);
    }

    @Test
    public void getValueToday_ShouldBySameAs_FunctionToShiftYesterday_IfDatesToShiftIsOne() throws Exception {
        when(functionToShift.getValue(YESTERDAY)).thenReturn(VALUE);
        assertThat(shiftedFunction.getValue(TODAY)).isEqualTo(functionToShift.getValue(YESTERDAY));
    }

    @Test
    public void afterRegisteringListenerOnDiffFunction_ListenerGetsCalledWhenChangeIsDetectedInDiffFunction() throws Exception {
        shiftedFunction.addListener(functionListener);
        shiftedFunction.changeDetected();
        verify(functionListener).changeDetected();
    }

}