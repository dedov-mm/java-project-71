package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import hexlet.code.FormatterSelection;

import java.util.List;

public class StylishTextFormatter implements FormatterSelection {
    @Override
    public final String format(List<DiffEntry> diff) {
        StringBuilder result = new StringBuilder("{\n");
        for (DiffEntry entry : diff) {
            switch (entry.getType()) {
                case ADDED -> result
                        .append("  + ")
                        .append(entry.getKey())
                        .append(": ")
                        .append(entry.getNewValue())
                        .append("\n");
                case REMOVED -> result
                        .append("  - ")
                        .append(entry.getKey())
                        .append(": ")
                        .append(entry.getOldValue())
                        .append("\n");
                case CHANGED -> {
                    result
                            .append("  - ")
                            .append(entry.getKey())
                            .append(": ")
                            .append(entry.getOldValue())
                            .append("\n");
                    result
                            .append("  + ")
                            .append(entry.getKey())
                            .append(": ")
                            .append(entry.getNewValue())
                            .append("\n");
                }
                case UNCHANGED -> result
                        .append("    ")
                        .append(entry.getKey())
                        .append(": ")
                        .append(entry.getOldValue())
                        .append("\n");
                default -> throw new IllegalArgumentException("Unknown diff type: " + entry.getType());
            }
        }
        result.append("}");
        return result.toString();
    }
}
