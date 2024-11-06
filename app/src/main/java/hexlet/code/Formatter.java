package hexlet.code;

import hexlet.code.formatters.PlainTextFormatter;
import hexlet.code.formatters.StylishTextFormatter;

public class Formatter {

    public static FormatterSelection getFormatter(String format) {
        return switch (format.toLowerCase()) {
            case "stylish" -> new StylishTextFormatter();
            case "plain" -> new PlainTextFormatter();
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        };
    }
}
