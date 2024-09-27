package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {
    public static String generate(File file1, File file2) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map1 = mapper.readValue(file1, Map.class);
        Map<String, Object> map2 = mapper.readValue(file2, Map.class);
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        StringBuilder result = new StringBuilder();
        result.append("{\n");
        for (String key : allKeys) {
            if (!map1.containsKey(key)) {
                // Ключ есть только во втором файле
                result.append("  + ").append(key).append(": ").append(map2.get(key)).append("\n");
            } else if (!map2.containsKey(key)) {
                // Ключ есть только в первом файле
                result.append("  - ").append(key).append(": ").append(map1.get(key)).append("\n");
            } else {
                // Ключ есть в обоих файлах
                if (map1.get(key).equals(map2.get(key))) {
                    result.append("    ").append(key).append(": ").append(map1.get(key)).append("\n");
                } else {
                    // Ключ присутствует в обоих файлах, но значения отличаются
                    result.append("  - ").append(key).append(": ").append(map1.get(key)).append("\n");
                    result.append("  + ").append(key).append(": ").append(map2.get(key)).append("\n");
                }
            }
        }
        result.append("}");
        return result.toString();
    }
}
