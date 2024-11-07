package hexlet.code.formatters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;
import hexlet.code.FormatterSelection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonFormatterTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testFormatWithChanges() throws Exception {
        FormatterSelection formatter = new JsonFormatter();

        List<DiffEntry> diff = List.of(
                new DiffEntry("key1", "value1", null, DiffEntry.DiffType.REMOVED),
                new DiffEntry("key2", null, "value2", DiffEntry.DiffType.ADDED),
                new DiffEntry("key3", "oldValue", "newValue", DiffEntry.DiffType.CHANGED)
        );

        String expectedJson = """
                [
                    {"key": "key1", "type": "removed", "oldValue": "value1", "newValue": null},
                    {"key": "key2", "type": "added", "oldValue": null, "newValue": "value2"},
                    {"key": "key3", "type": "changed", "oldValue": "oldValue", "newValue": "newValue"}
                ]
                """;

        JsonNode expectedTree = objectMapper.readTree(expectedJson);
        JsonNode actualTree = objectMapper.readTree(formatter.format(diff));

        assertEquals(expectedTree, actualTree);
    }

    @Test
    void testFormatWithNoChanges() throws Exception {
        FormatterSelection formatter = new JsonFormatter();

        List<DiffEntry> diff = List.of(
                new DiffEntry("key1", "value1", "value1", DiffEntry.DiffType.UNCHANGED)
        );

        String expectedJson = """
                [
                    {"key": "key1", "type": "unchanged", "oldValue": "value1", "newValue": "value1"}
                ]
                """;

        JsonNode expectedTree = objectMapper.readTree(expectedJson);
        JsonNode actualTree = objectMapper.readTree(formatter.format(diff));

        assertEquals(expectedTree, actualTree);
    }
}
