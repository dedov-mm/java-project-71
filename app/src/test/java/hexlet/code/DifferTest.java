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
        var file1 = getFixturePath("file1.json");
        var file2 = getFixturePath("file2.json");

        var actual = Differ.generate(file1, file2);
        var expectedPath = getFixturePath("result.txt");
        var expected = Files.readString(expectedPath);
        assertEquals(expected, actual);
    }
}
