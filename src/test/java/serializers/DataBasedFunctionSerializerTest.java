package serializers;

import functions.DataBasedFunction;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class DataBasedFunctionSerializerTest {

    private DataBasedFunctionSerializer serializer = new DataBasedFunctionSerializer(new LocalDataSerializer());

    @Test
    public void notSoFullChainTest() throws Exception {

        DataBasedFunction actualFunction = new DataBasedFunction();
        actualFunction.addValue(LocalDate.now(), 17.2);
        byte[] bytes = serializer.serialize(actualFunction);

        Assertions.assertThat(serializer.deserialize(bytes)).isEqualTo(actualFunction);
    }

    @Test
    public void fullChainTest() throws Exception {

        DataBasedFunction actualFunction = new DataBasedFunction();
        actualFunction.addValue(LocalDate.now(), 17.2);
        actualFunction.addValue(LocalDate.now().plusDays(1L) , 5.5);
        byte[] bytes = serializer.serialize(actualFunction);

        Assertions.assertThat(serializer.deserialize(bytes)).isEqualTo(actualFunction);
    }
}