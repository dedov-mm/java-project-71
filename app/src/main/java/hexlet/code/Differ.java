package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {
    public static String generate(Path path1, Path path2) throws Exception {
        if (!Files.exists(path1) || !Files.exists(path2)) {
            throw new IOException("Files do not exist");
        }

        ObjectMapper mapper1 = getMapperByFileExtension(path1);
        ObjectMapper mapper2 = getMapperByFileExtension(path2);

        Map<String, Object> map1 = mapper1.readValue(Files.newInputStream(path1), Map.class);
        Map<String, Object> map2 = mapper2.readValue(Files.newInputStream(path2), Map.class);
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        StringBuilder result = new StringBuilder();
        result.append("{\n");
        for (String key : allKeys) {
            if (!map1.containsKey(key)) {
                result.append("  + ").append(key).append(": ").append(map2.get(key)).append("\n");
            } else if (!map2.containsKey(key)) {
                result.append("  - ").append(key).append(": ").append(map1.get(key)).append("\n");
            } else {
                if (map1.get(key).equals(map2.get(key))) {
                    result.append("    ").append(key).append(": ").append(map1.get(key)).append("\n");
                } else {
                    result.append("  - ").append(key).append(": ").append(map1.get(key)).append("\n");
                    result.append("  + ").append(key).append(": ").append(map2.get(key)).append("\n");
                }
            }
        }
        result.append("}");
        return result.toString();
    }

    private static ObjectMapper getMapperByFileExtension(Path path) throws IllegalArgumentException {
        String fileName = path.getFileName().toString().toLowerCase();

        if (fileName.endsWith(".json")) {
            return new ObjectMapper();
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            return new ObjectMapper(new YAMLFactory());
        } else {
            throw new IllegalArgumentException("Wrong format:" + fileName);
        }
    }
}
