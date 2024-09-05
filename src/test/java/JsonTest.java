import com.fasterxml.jackson.databind.ObjectMapper;
import model.ModelIdJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;


public class JsonTest {
    ClassLoader cl = JsonTest.class.getClassLoader();
    static final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void readerJsonAndParsToClassTest() throws Exception {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(cl.getResourceAsStream("test-json.json")))) {
            ModelIdJson mid = objectMapper.readValue(reader, ModelIdJson.class);

                Assertions.assertEquals("1", mid.getId());
                Assertions.assertEquals("Alexander", mid.getName());
                Assertions.assertEquals("1989-11-14", mid.getDob());
            }
        }
    }




