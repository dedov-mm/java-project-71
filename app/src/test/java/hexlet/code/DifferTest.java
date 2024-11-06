package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        var path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }

    @Test
    void testGenerate() throws Exception {
        var filepath1 = getFixturePath("filepath1.json");
        var filepath2 = getFixturePath("filepath2.json");

        String actual = Differ.generate(filepath1, filepath2, "stylish");
        String expected = Files.readString(getFixturePath("resultstylish.txt"));

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateNested() throws Exception {
        var filepath1 = getFixturePath("filenested1.json");
        var filepath2 = getFixturePath("filenested2.json");

        String actual = Differ.generate(filepath1, filepath2, "stylish");
        String expected = readFixture("resultnestedstylish.txt");

        assertEquals(expected, actual);
    }
}
