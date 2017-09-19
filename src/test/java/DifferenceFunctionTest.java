import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import functions.DataBasedFunction;
import functions.DifferenceFunction;
import listener.FunctionListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class DifferenceFunctionTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private DataBasedFunction first_dataBasedFunction;

    @Mock
    private DataBasedFunction second_dataBasedFunction;

    @Mock
    private FunctionListener functionListener;

    @InjectMocks
    private DifferenceFunction differenceFunction;

    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusMonths(7);

    @Before
    public void setUp() {
        differenceFunction = new DifferenceFunction(first_dataBasedFunction, second_dataBasedFunction);
    }

    @Test
    public void getValue_OnDateWhichDoesntExistInBothFunctions_ShouldReturnZero() throws Exception {
        assertThat(differenceFunction.getValue(LONG_AGO.plusDays(1))).isEqualTo(0.0);
    }

    @Test
    public void getValue_OnToday_ShouldReturnMinusFive() throws Exception {
        when(first_dataBasedFunction.getValue(TODAY)).thenReturn(5.0);
        when(second_dataBasedFunction.getValue(TODAY)).thenReturn(10.0);
        assertThat(differenceFunction.getValue(TODAY)).isEqualTo(-5.0);
    }

    @Test
    public void getValue_OnOneWeekAgo_ShouldReturnFour() throws Exception {
        when(first_dataBasedFunction.getValue(ONE_WEEK_AGO)).thenReturn(10.0);
        when(second_dataBasedFunction.getValue(ONE_WEEK_AGO)).thenReturn(6.0);
        assertThat(differenceFunction.getValue(ONE_WEEK_AGO)).isEqualTo(4.0);
    }

    @Test
    public void getValue_OnDayPresentInFirstNotInSecond_ShouldReturnValueOfFirst() throws Exception {
        when(first_dataBasedFunction.getValue(LONG_AGO)).thenReturn(20.0);
        assertThat(differenceFunction.getValue(LONG_AGO)).isEqualTo(20.0);
    }

    @Test
    public void afterRegisteringListenerOnFirstFunction_ListenerGetsCalledWhenChangeIsDetected() throws Exception {
        differenceFunction.addListener(functionListener);
        differenceFunction.changeDetected();
        verify(functionListener).changeDetected();
    }

}