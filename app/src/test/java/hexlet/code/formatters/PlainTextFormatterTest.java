package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import hexlet.code.DiffEntry.DiffType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTextFormatterTest {

    @Test
    void testFormat() {
        List<DiffEntry> diff = List.of(
                new DiffEntry("chars2", null, false, DiffType.CHANGED),
                new DiffEntry("checked", false, true, DiffType.CHANGED),
                new DiffEntry("default", null, List.of("complex", "value"), DiffType.CHANGED),
                new DiffEntry("id", 45, null, DiffType.CHANGED),
                new DiffEntry("key1", "value1", null, DiffType.REMOVED),
                new DiffEntry("key2", null, "value2", DiffType.ADDED),
                new DiffEntry("numbers2", Map.of("key", "value"), Map.of("key", "value"), DiffType.CHANGED),
                new DiffEntry("numbers3", List.of(1, 2), null, DiffType.REMOVED),
                new DiffEntry("numbers4", null, List.of(3, 4), DiffType.ADDED),
                new DiffEntry("obj1", null, Map.of("nestedKey", "nestedValue"), DiffType.ADDED),
                new DiffEntry("setting1", "Some value", "Another value", DiffType.CHANGED),
                new DiffEntry("setting2", 200, 300, DiffType.CHANGED),
                new DiffEntry("setting3", true, "none", DiffType.CHANGED)
        );

        PlainTextFormatter formatter = new PlainTextFormatter();

        String actual = formatter.format(diff);

        String expected = """
                Property 'chars2' was updated. From null to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

        assertEquals(expected, actual);
    }
}
