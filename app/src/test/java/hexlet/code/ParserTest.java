package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static Map<String, Object> readExpectedMap(String fileName) throws Exception {
        Path path = getFixturePath(fileName);

        ObjectMapper mapper;
        if (fileName.endsWith(".json")) {
            mapper = new ObjectMapper();
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + fileName);
        }

        return mapper.readValue(path.toFile(), new TypeReference<Map<String, Object>>() { });
    }

    @Test
    void testParseJson() throws Exception {
        Path jsonFile = getFixturePath("filepath1.json");
        Map<String, Object> actual = Parser.parse(jsonFile);

        var expected = readExpectedMap("filepath1.json");

        assertEquals(expected, actual);
    }

    @Test
    void testParseYaml() throws Exception {
        Path yamlFile = getFixturePath("filepath1.yml");
        Map<String, Object> actual = Parser.parse(yamlFile);

        var expected = readExpectedMap("filepath1.yml");

        assertEquals(expected, actual);
    }

    @Test
    void testUnsupportedFormat() {
        Path txtFile = Paths.get("app/src/test/resources/fixtures/filepath1.txt");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse(txtFile);
        });

        assertEquals("Unsupported format: filepath1.txt", exception.getMessage());
    }
}
