import functions.DataBasedFunction;
import functions.Function;
import functions.PerformanceFunction;
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

import javax.xml.crypto.Data;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PerformanceFunctionTest {

    private static final double TSS = 42.6;
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final LocalDate LONG_AGO = LocalDate.now().minusMonths(5);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private DataBasedFunction tssFunction;

    @Mock
    private FunctionListener functionListener;

    @InjectMocks
    private PerformanceFunction performanceFunction;

    @Before
    public void setUp() throws Exception {
        performanceFunction = new PerformanceFunction(tssFunction, 42, YESTERDAY);
    }

    @Test
    public void getValue_applyPositiveTss_functionShouldRise() throws Exception {
        when(tssFunction.getValue(YESTERDAY)).thenReturn(0.0);
        when(performanceFunction.getValue(YESTERDAY)).thenReturn(0.0);
        when(tssFunction.getValue(TODAY)).thenReturn(TSS);

        assertRisingFunction(performanceFunction, TODAY);
    }

    @Test
    public void getValue_applyZeroTss_functionShouldDecline() throws Exception {
        when(performanceFunction.getValue(YESTERDAY)).thenReturn(1.0);
        when(tssFunction.getValue(TODAY)).thenReturn(0.0);

        assertDecreasingFunction(performanceFunction, TODAY);

    }

    @Test
    public void getValue_StartDateBeforeFirstTraining() throws Exception {
        when(tssFunction.getValue(LONG_AGO)).thenReturn(0.0);
        when(tssFunction.getValue(LONG_AGO.minusDays(1))).thenReturn(0.0);
        Assertions.assertThat(performanceFunction.getValue(LONG_AGO.minusDays(1))).isEqualTo(0.0);
    }

    @Test
    public void subscribe_afterRegisteringListenerOnTssFUnction_ListenerGetsCalledWhenChangeIsDetected() throws Exception {
        performanceFunction.addListener(functionListener);
        performanceFunction.changeDetected();
        verify(functionListener).changeDetected();
    }

    private void assertRisingFunction(Function function, LocalDate date) {
        assertTrue(function.getValue(date) > function.getValue(date.minusDays(1)));
    }

    private void assertDecreasingFunction(Function function, LocalDate date) {
        assertTrue(function.getValue(date) < function.getValue(date.minusDays(1)));
    }

}