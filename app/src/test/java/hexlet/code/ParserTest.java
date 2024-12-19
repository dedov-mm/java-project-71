package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
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

    private static String readFileContent(String fileName) throws Exception {
        Path path = getFixturePath(fileName);
        return Files.readString(path);
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
        String jsonContent = readFileContent("filepath1.json");
        Map<String, Object> actual = Parser.parse(jsonContent, "json");

        var expected = readExpectedMap("filepath1.json");

        assertEquals(expected, actual);
    }

    @Test
    void testParseYaml() throws Exception {
        String yamlContent = readFileContent("filepath1.yml");
        Map<String, Object> actual = Parser.parse(yamlContent, "yaml");

        var expected = readExpectedMap("filepath1.yml");

        assertEquals(expected, actual);
    }

    @Test
    void testUnsupportedFormat() {
        String invalidContent = "filepath1.txt";
        String unsupportedFormat = "txt";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse(invalidContent, unsupportedFormat);
        });

        assertEquals("Unsupported format: txt", exception.getMessage());
    }
}
