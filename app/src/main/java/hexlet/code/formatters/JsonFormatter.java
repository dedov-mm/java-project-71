package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.DiffEntry;
import hexlet.code.FormatterSelection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonFormatter implements FormatterSelection {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT) // Включаем pretty-print
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true); // Сортировка полей по ключам

    @Override
    public final String format(List<DiffEntry> diff) throws Exception {
        List<Map<String, Object>> formattedDiff = diff.stream()
                .sorted((entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey())) // Сортировка по ключу
                .map(entry -> {
                    // Создаем Map через HashMap для поддержки null значений
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
