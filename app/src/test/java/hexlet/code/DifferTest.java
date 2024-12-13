package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    void testGeneratePlainFromJson() throws Exception {
        var filepath1 = getFixturePath("filenested1.json").toString();
        var filepath2 = getFixturePath("filenested2.json").toString();

        String actual = Differ.generate(filepath1, filepath2, "plain");
        String expected = readFixture("resultplain.txt");

        assertEquals(expected, actual);
    }

    @Test
    void testGeneratePlainFromYml() throws Exception {
        var filepath1 = getFixturePath("filenested1.yml").toString();
        var filepath2 = getFixturePath("filenested2.yml").toString();

        String actual = Differ.generate(filepath1, filepath2, "plain");
        String expected = readFixture("resultplain.txt");

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateStylishFromJson() throws Exception {
        var filepath1 = getFixturePath("filenested1.json").toString();
        var filepath2 = getFixturePath("filenested2.json").toString();

        String actual = Differ.generate(filepath1, filepath2, "stylish");
        String expected = readFixture("resultnestedstylish.txt");

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateStylishFromYml() throws Exception {
        var filepath1 = getFixturePath("filenested1.yml").toString();
        var filepath2 = getFixturePath("filenested2.yml").toString();

        String actual = Differ.generate(filepath1, filepath2, "stylish");
        String expected = readFixture("resultnestedstylish.txt");

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateJsonFromJson() throws Exception {
        var file1Path = getFixturePath("filenested1.json").toString();
        var file2Path = getFixturePath("filenested2.json").toString();

        String actualJsonOutputStr = Differ.generate(file1Path, file2Path, "json");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> actualJsonOutput = objectMapper.readValue(
                actualJsonOutputStr, new TypeReference<List<Map<String, Object>>>() { }
        );

        String expectedJsonOutputStr = readFixture("resultjson.json");

        List<Map<String, Object>> expectedJsonOutput = objectMapper.readValue(
                expectedJsonOutputStr, new TypeReference<List<Map<String, Object>>>() { }
        );

        List<Map<String, Object>> sortedExpected = expectedJsonOutput.stream()
                .sorted(Comparator.comparing(map -> map.get("key").toString()))
                .collect(Collectors.toList());
        List<Map<String, Object>> sortedActual = actualJsonOutput.stream()
                .sorted(Comparator.comparing(map -> map.get("key").toString()))
                .collect(Collectors.toList());

        assertEquals(sortedExpected, sortedActual);
    }

    @Test
    void testGenerateJsonFromYml() throws Exception {
        var filePath1 = getFixturePath("filenested1.yml").toString();
        var filePath2 = getFixturePath("filenested2.yml").toString();

        String actualJsonOutputStr = Differ.generate(filePath1, filePath2, "json");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> actualJsonOutput = objectMapper.readValue(
                actualJsonOutputStr, new TypeReference<List<Map<String, Object>>>() { }
        );

        String expectedJsonOutputStr = readFixture("resultjson.json");

        List<Map<String, Object>> expectedJsonOutput = objectMapper.readValue(
                expectedJsonOutputStr, new TypeReference<List<Map<String, Object>>>() { }
        );

        List<Map<String, Object>> sortedExpected = expectedJsonOutput.stream()
                .sorted(Comparator.comparing(map -> map.get("key").toString()))
                .collect(Collectors.toList());
        List<Map<String, Object>> sortedActual = actualJsonOutput.stream()
                .sorted(Comparator.comparing(map -> map.get("key").toString()))
                .collect(Collectors.toList());

        assertEquals(sortedExpected, sortedActual);
    }

    @Test
    void testGenerateDefaultFromJson() throws Exception {
        var filepath1 = getFixturePath("filepath1.json").toString();
        var filepath2 = getFixturePath("filepath2.json").toString();

        String actual = Differ.generate(filepath1, filepath2);
        String expected = Files.readString(getFixturePath("resultstylish.txt")).trim();

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateDefaultFromYml() throws Exception {
        var filepath1 = getFixturePath("filepath1.yml").toString();
        var filepath2 = getFixturePath("filepath2.yml").toString();

        String actual = Differ.generate(filepath1, filepath2);
        String expected = Files.readString(getFixturePath("resultstylish.txt")).trim();

        assertEquals(expected, actual);
    }
}
