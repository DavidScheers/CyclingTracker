import main.Functions.Function;
import main.Functions.ZeroOrderHoldFunction;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by davids on 2/03/2017.
 */
public class ZeroOrderHoldFunctionTest {

    private Function zeroOrderHoldFunction_WithData;
    private Function zeroOrderHoldFunction_Empty;
    private Map<LocalDate, Double> valueMap_WithData;
    private Map<LocalDate, Double> valueMap_Empty;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate YESTERYESTERDAY = LocalDate.now().minusDays(2);
    private final LocalDate ONE_WEEK_AGO = LocalDate.now().minusDays(7);
    private final LocalDate LONG_AGO = LocalDate.now().minusDays(7);

    @Before
    public void setUp() {
        valueMap_WithData = new HashMap<>();
        valueMap_Empty = new HashMap<>();
        getData();
        zeroOrderHoldFunction_WithData = new ZeroOrderHoldFunction(valueMap_WithData);
        zeroOrderHoldFunction_Empty = new ZeroOrderHoldFunction(valueMap_Empty);
    }

    private void getData() {
        valueMap_WithData.put(LONG_AGO, 0.55);
        valueMap_WithData.put(ONE_WEEK_AGO, 0.35);
        valueMap_WithData.put(YESTERYESTERDAY, 0.8);
        valueMap_WithData.put(TODAY, 0.65);
    }

    @Test
    public void EmptyMap_ShouldReturnZero() throws Exception {
        assertTrue(zeroOrderHoldFunction_Empty.getValue(TODAY) == 0);
    }

    @Test
    public void MapWithData_ShouldReturnValue_WhenDateIsPresent_MostRecentDate() throws Exception {
        assertTrue(zeroOrderHoldFunction_WithData.getValue(TODAY) == 0.65);
    }

    @Test
    public void MapWithData_ShouldReturnValue_WhenDateIsPresent_DateInPast() throws Exception {
        assertTrue(zeroOrderHoldFunction_WithData.getValue(ONE_WEEK_AGO) == 0.35);
    }

    @Test
    public void MapWithData_ShouldReturnFirstExistingValue_WhenAskedForNotPresentDay() throws Exception {
        assertTrue(zeroOrderHoldFunction_WithData.getValue(YESTERDAY) == 0.8);
    }

}