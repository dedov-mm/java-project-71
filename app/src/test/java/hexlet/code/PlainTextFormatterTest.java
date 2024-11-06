package hexlet.code;

import hexlet.code.formatters.PlainTextFormatter;
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

        List<DiffEntry> diff = List.of(
                new DiffEntry("chars2", "[complex value]", false, DiffEntry.DiffType.CHANGED),
                new DiffEntry("checked", false, true, DiffEntry.DiffType.CHANGED),
                new DiffEntry("default", null, "[complex value]", DiffEntry.DiffType.CHANGED),
                new DiffEntry("id", 45, null, DiffEntry.DiffType.CHANGED),
                new DiffEntry("key1", "value1", null, DiffEntry.DiffType.REMOVED),
                new DiffEntry("key2", null, "value2", DiffEntry.DiffType.ADDED),
                new DiffEntry("numbers2", "[complex value]", "[complex value]", DiffEntry.DiffType.CHANGED),
                new DiffEntry("numbers3", "[complex value]", null, DiffEntry.DiffType.REMOVED),
                new DiffEntry("numbers4", null, "[complex value]", DiffEntry.DiffType.ADDED),
                new DiffEntry("obj1", null, "[complex value]", DiffEntry.DiffType.ADDED),
                new DiffEntry("setting1", "Some value", "Another value", DiffEntry.DiffType.CHANGED),
                new DiffEntry("setting2", 200, 300, DiffEntry.DiffType.CHANGED),
                new DiffEntry("setting3", true, "none", DiffEntry.DiffType.CHANGED)
        );

        String result = formatter.format(diff);
        String expected = Files.readString(getFixturePath("resultplain.txt"));

        assertEquals(expected, result);
    }

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }
}
