package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public static Map<String, Object> parse(String content, String format) throws Exception {
        ObjectMapper mapper = switch (format.toLowerCase()) {
            case "json" -> JSON_MAPPER;
            case "yaml" -> YAML_MAPPER;
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };

        return mapper.readValue(content, new TypeReference<>() { });
    }
}
