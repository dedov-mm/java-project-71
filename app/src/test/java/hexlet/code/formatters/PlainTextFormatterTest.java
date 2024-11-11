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
    @Test
    void testFormat() throws Exception {
        PlainTextFormatter formatter = new PlainTextFormatter();
        List<DiffEntry> diff = readDiffFixture("diffplain.json");
        String actual = formatter.format(diff);
        String expected = Files.readString(getFixturePath("resultplain.txt"));

        assertEquals(expected, actual);
    }

    private List<DiffEntry> readDiffFixture(String fileName) throws Exception {
        Path path = getFixturePath(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(Files.newInputStream(path), new TypeReference<List<DiffEntry>>() {});
    }

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }
}
