package serializers;

import functions.DataBasedFunction;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DataBasedFunctionSerializerTest {

    private DataBasedFunctionSerializer serializer = new DataBasedFunctionSerializer(new LocalDateSerializer());

    @Test
    public void notSoFullChainTest() throws Exception {

        DataBasedFunction actualFunction = new DataBasedFunction();
        actualFunction.addValue(LocalDate.now(), 17.2);
        byte[] bytes = serializer.serialize(actualFunction);

        assertThat(serializer.deserialize(bytes)).isEqualTo(actualFunction);
    }

    @Test
    public void fullChainTest() throws Exception {

        DataBasedFunction actualFunction = new DataBasedFunction();
        actualFunction.addValue(LocalDate.now(), 17.2);
        actualFunction.addValue(LocalDate.now().plusDays(1L) , 5.5);
        byte[] bytes = serializer.serialize(actualFunction);

        assertThat(serializer.deserialize(bytes)).isEqualTo(actualFunction);
    }
}