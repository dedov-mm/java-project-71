package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @ParameterizedTest
    @CsvSource({
            "filenested1.json, filenested2.json, plain, resultplain.txt",
            "filenested1.yml, filenested2.yml, plain, resultplain.txt",
            "filenested1.json, filenested2.json, stylish, resultnestedstylish.txt",
            "filenested1.yml, filenested2.yml, stylish, resultnestedstylish.txt",
            "filenested1.json, filenested2.json, json, resultjson.json",
            "filenested1.yml, filenested2.yml, json, resultjson.json"
    })
    public void generateTest(String file1, String file2, String format, String expectedFile) throws Exception {
        var filePath1 = getFixturePath(file1).toString();
        var filePath2 = getFixturePath(file2).toString();

        String actual = Differ.generate(filePath1, filePath2, format);
        String expected = readFixture(expectedFile);

        if ("json".equals(format)) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode actualJson = objectMapper.readTree(actual);
            JsonNode expectedJson = objectMapper.readTree(expected);

            normalizeJson(actualJson);
            normalizeJson(expectedJson);

            assertEquals(actualJson, expectedJson);
        } else {
            assertEquals(expected, actual);
        }
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
}
