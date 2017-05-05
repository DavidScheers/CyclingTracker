package serializers;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateSerializerTest {

    private LocalDateSerializer serializer = new LocalDateSerializer();

    @Test
    public void testFullChain() throws Exception {
        LocalDate actualDate = LocalDate.now();
        byte[] bytes = serializer.serialize(actualDate);

        assertThat(serializer.deserialize(bytes)).isEqualTo(actualDate);
    }
}