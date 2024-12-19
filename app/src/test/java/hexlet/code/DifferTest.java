package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String expectedJsonOutputStr = readFixture("resultjson.json");

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode actualJson = objectMapper.readTree(actualJsonOutputStr);
        JsonNode expectedJson = objectMapper.readTree(expectedJsonOutputStr);

        normalizeJson(actualJson);
        normalizeJson(expectedJson);

        assertEquals(actualJson, expectedJson);
    }

    private void normalizeJson(JsonNode node) {
        if (node.isObject()) {
            node.fieldNames().forEachRemaining(field -> {
                normalizeJson(node.get(field));
            });
        } else if (node.isArray()) {
            sortArrayNode((ArrayNode) node);
            for (JsonNode element : node) {
                normalizeJson(element);
            }
        }
    }

    private void sortArrayNode(ArrayNode arrayNode) {
        List<JsonNode> list = new ArrayList<>();
        Iterator<JsonNode> elements = arrayNode.elements();
        while (elements.hasNext()) {
            list.add(elements.next());
        }

        list.sort((a, b) -> a.toString().compareTo(b.toString()));

        arrayNode.removeAll();
        for (JsonNode element : list) {
            arrayNode.add(element);
        }
    }

    @Test
    void testGenerateJsonFromYml() throws Exception {
        var filePath1 = getFixturePath("filenested1.yml").toString();
        var filePath2 = getFixturePath("filenested2.yml").toString();

        String actualJsonOutputStr = Differ.generate(filePath1, filePath2, "json");
        String expectedJsonOutputStr = readFixture("resultjson.json");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualJson = objectMapper.readTree(actualJsonOutputStr);
        JsonNode expectedJson = objectMapper.readTree(expectedJsonOutputStr);

        normalizeJson(actualJson);
        normalizeJson(expectedJson);

        assertEquals(actualJson, expectedJson);
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
