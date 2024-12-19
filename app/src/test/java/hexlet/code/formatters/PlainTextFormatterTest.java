package hexlet.code.formatters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTextFormatterTest {
    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        var path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }

    private static <T> T parseJson(String json, TypeReference<T> typeReference) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, typeReference);
    }

    @Test
    void testFormatWithFixtures() throws Exception {
        String diffJson = readFixture("diffEntries.json");
        List<DiffEntry> diff = parseJson(diffJson, new TypeReference<>() { });

        String expected = readFixture("expectedPlainText.txt");

        PlainTextFormatter formatter = new PlainTextFormatter();
        String actual = formatter.format(diff);

        assertEquals(expected, actual);
    }
}
