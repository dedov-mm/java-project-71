package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import hexlet.code.FormatterSelection;

import java.util.List;
import java.util.Map;

public class PlainTextFormatter implements FormatterSelection {
    @Override
    public final String format(List<DiffEntry> diff) {
        StringBuilder result = new StringBuilder();
        for (DiffEntry entry : diff) {
            String key = entry.getKey();
            switch (entry.getType()) {
                case ADDED -> result.append("Property '").append(key)
                        .append("' was added with value: ")
                        .append(stringify(entry.getNewValue())).append("\n");
                case REMOVED -> result.append("Property '").append(key).append("' was removed\n");
                case CHANGED -> result.append("Property '").append(key)
                        .append("' was updated. From ")
                        .append(stringify(entry.getOldValue()))
                        .append(" to ")
                        .append(stringify(entry.getNewValue())).append("\n");
                default -> { }
            }
        }
        return result.toString().trim();
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String) {
            if (value.equals("[complex value]")) {
                return (String) value;
            }
            return "'" + value + "'";
        }

        if (value instanceof Map || value instanceof List) {
            return "[complex value]";
        }

        return value.toString();
    }
}
