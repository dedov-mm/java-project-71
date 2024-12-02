package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String content) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

        try {
            return jsonMapper.readValue(content, new TypeReference<Map<String, Object>>() { });
        } catch (Exception e) {
            try {
                return yamlMapper.readValue(content, new TypeReference<Map<String, Object>>() { });
            } catch (Exception yamlException) {
                throw new IllegalArgumentException("Unsupported format: " + content);
            }
        }
    }
}
