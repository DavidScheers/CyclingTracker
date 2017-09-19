import functions.DataBasedFunction;
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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class DataBasedFunctionTest {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private FunctionListener functionListener;

    private DataBasedFunction dataBasedFunction;

    @Before
    public void setUp() {
        dataBasedFunction = new DataBasedFunction();
        getData();
    }

    @Test
    public void getValue_ForToday_shouldReturnFive() throws Exception {
        assertThat(dataBasedFunction.getValue(TODAY)).isEqualTo(5.0);
    }

    @Test
    public void getValue_ForYesterday_ShouldReturnSix() throws Exception {
        assertThat(dataBasedFunction.getValue(YESTERDAY)).isEqualTo(6.0);
    }

    @Test
    public void getValue_ForNotExistingDay_ShouldReturnZero() throws Exception {
        assertThat(dataBasedFunction.getValue(ONE_WEEK_AGO.plusDays(1))).isEqualTo(0.0);
    }

    @Test
    public void afterRegisteringListener_ListenersGetsCalledWhenDataBasedFunctionChanges() throws Exception {
        dataBasedFunction.addListener(functionListener);
        dataBasedFunction.addValue(ONE_WEEK_AGO.plusDays(1), 5.0);
        verify(functionListener).changeDetected();
    }

    private void getData() {
        dataBasedFunction.addValue(TODAY, 5.0);
        dataBasedFunction.addValue(YESTERDAY, 6.0);
        dataBasedFunction.addValue(ONE_WEEK_AGO, 10.0);
    }

}
