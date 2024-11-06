package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import hexlet.code.FormatterSelection;

import java.util.List;
import java.util.Map;

public class PlainTextFormatter implements FormatterSelection {
    @Override
    public String format(List<DiffEntry> diff) {
        StringBuilder result = new StringBuilder();
        for (DiffEntry entry : diff) {
            String key = entry.getKey();
            switch (entry.getType()) {
                case ADDED -> result.append("Property '").append(key)
                        .append("' was added with value: ")
                        .append(formatValue(entry.getNewValue())).append("\n");
                case REMOVED -> result.append("Property '").append(key).append("' was removed\n");
                case CHANGED -> result.append("Property '").append(key)
                        .append("' was updated. From ")
                        .append(formatValue(entry.getOldValue()))
                        .append(" to ")
                        .append(formatValue(entry.getNewValue())).append("\n");
                default -> { }
            }
        }
        return result.toString().trim();
    }

    private String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String && !"[complex value]".equals(value)) {
            return "'" + value + "'";
        } else if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        }
        return value.toString();
    }
}
