package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static hexlet.code.DiffEntry.DiffType.ADDED;
import static hexlet.code.DiffEntry.DiffType.CHANGED;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonFormatterTest {

    private JsonFormatter jsonFormatter;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        jsonFormatter = new JsonFormatter();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFormatSingleDiffEntry() throws Exception {
        DiffEntry diffEntry = new DiffEntry("key1", "oldValue1", "newValue1", CHANGED);
        List<DiffEntry> diffList = Arrays.asList(diffEntry);

        String expectedJson = "[{\"key\":\"key1\",\"oldValue\":\"oldValue1\",\"newValue\":\"newValue1\",\"type\":\"CHANGED\"}]";
        String actualJson = jsonFormatter.format(diffList);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    void testFormatMultipleDiffEntries() throws Exception {
        DiffEntry diffEntry1 = new DiffEntry("key1", "oldValue1", "newValue1", CHANGED);
        DiffEntry diffEntry2 = new DiffEntry("key2", "oldValue2", "newValue2", ADDED);
        List<DiffEntry> diffList = Arrays.asList(diffEntry1, diffEntry2);

        String expectedJson = "[{\"key\":\"key1\",\"oldValue\":\"oldValue1\",\"newValue\":\"newValue1\",\"type\":\"CHANGED\"}," +
                "{\"key\":\"key2\",\"oldValue\":\"oldValue2\",\"newValue\":\"newValue2\",\"type\":\"ADDED\"}]";
        String actualJson = jsonFormatter.format(diffList);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    void testFormatEmptyDiffList() throws Exception {
        List<DiffEntry> diffList = Arrays.asList();

        String expectedJson = "[]";
        String actualJson = jsonFormatter.format(diffList);

        assertEquals(expectedJson, actualJson);
    }
}
