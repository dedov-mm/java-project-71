package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static hexlet.code.DiffEntry.DiffType.ADDED;
import static hexlet.code.DiffEntry.DiffType.CHANGED;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonFormatterTest {

    private JsonFormatter jsonFormatter;
    private ObjectMapper objectMapper;

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        var path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }

    @BeforeEach
    void setUp() {
        jsonFormatter = new JsonFormatter();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFormatSingleDiffEntry() throws Exception {
        DiffEntry diffEntry = new DiffEntry("key1", "oldValue1", "newValue1", CHANGED);
        List<DiffEntry> diffList = Arrays.asList(diffEntry);

        String expectedJson = readFixture("expected_single_diff.json");
        String actualJson = jsonFormatter.format(diffList);

        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(actualJson));
    }

    @Test
    void testFormatMultipleDiffEntries() throws Exception {
        DiffEntry diffEntry1 = new DiffEntry("key1", "oldValue1", "newValue1", CHANGED);
        DiffEntry diffEntry2 = new DiffEntry("key2", "oldValue2", "newValue2", ADDED);
        List<DiffEntry> diffList = Arrays.asList(diffEntry1, diffEntry2);

        String expectedJson = readFixture("expected_multiple_diff.json");
        String actualJson = jsonFormatter.format(diffList);

        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(actualJson));
    }

    @Test
    void testFormatEmptyDiffList() throws Exception {
        List<DiffEntry> diffList = Arrays.asList();

        String expectedJson = readFixture("expected_empty_diff.json");
        String actualJson = jsonFormatter.format(diffList);

        assertEquals(expectedJson, actualJson);
    }
}
