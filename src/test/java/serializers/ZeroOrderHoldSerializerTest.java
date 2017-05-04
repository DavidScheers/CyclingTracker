package serializers;

import functions.ZeroOrderHoldFunction;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


public class ZeroOrderHoldSerializerTest {

    private ZeroOrderHoldSerializer serializer = new ZeroOrderHoldSerializer(new LocalDateSerializer());

    @Test
    public void fullChainTest() throws Exception {
        ZeroOrderHoldFunction actualFunction = new ZeroOrderHoldFunction();
        actualFunction.addValue(LocalDate.now(), 17.2);
        actualFunction.addValue(LocalDate.now(), 5.4);

        byte[] byteArray = serializer.serialize(actualFunction);

        assertThat(serializer.deserialize(byteArray)).isEqualTo(actualFunction);
    }
}