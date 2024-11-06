package hexlet.code;

import hexlet.code.formatters.PlainTextFormatter;
import hexlet.code.formatters.StylishTextFormatter;

import java.util.List;

public interface Formatter {
    String format(List<DiffEntry> diff);

    static Formatter getFormatter(String format) {
        return switch (format.toLowerCase()) {
            case "plain" -> new PlainTextFormatter();
            case "stylish" -> new StylishTextFormatter();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
