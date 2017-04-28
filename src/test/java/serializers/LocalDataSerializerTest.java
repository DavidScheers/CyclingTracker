package serializers;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LocalDataSerializerTest {

    private LocalDataSerializer serializer = new LocalDataSerializer();

    @Test
    public void testFullChain() throws Exception {
        LocalDate actualDate = LocalDate.now();
        byte[] bytes = serializer.serialize(actualDate);

        Assertions.assertThat(serializer.deserialize(bytes)).isEqualTo(actualDate);
    }
}