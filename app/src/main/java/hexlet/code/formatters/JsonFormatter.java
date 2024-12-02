package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;
import hexlet.code.FormatterSelection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonFormatter implements FormatterSelection {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public final String format(List<DiffEntry> diff) throws Exception {
        List<Map<String, Object>> formattedDiff = diff.stream()
                .sorted((entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey()))
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", entry.getKey());
                    map.put("type", entry.getType().toString().toLowerCase());
                    map.put("oldValue", entry.getOldValue());
                    map.put("newValue", entry.getNewValue());
                    return map;
                })
                .collect(Collectors.toList());

        return objectMapper.writeValueAsString(formattedDiff);
    }
}
