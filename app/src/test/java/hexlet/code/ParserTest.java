package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
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

    @Test
    void testParseJson() throws IOException {
        Path jsonFile = getFixturePath("filepath1.json");
        Map<String, Object> actual = Parser.parse(jsonFile);

        Map<String, Object> expected = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", "123.234.53.22",
                "follow", false
        );

        assertEquals(expected, actual);
    }

    @Test
    void testParseYaml() throws IOException {
        Path yamlFile = getFixturePath("filepath1.yml");
        Map<String, Object> actual = Parser.parse(yamlFile);

        Map<String, Object> expected = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", "123.234.53.22",
                "follow", false
        );

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
