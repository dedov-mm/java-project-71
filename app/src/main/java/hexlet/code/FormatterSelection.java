package hexlet.code;

import java.util.List;

public interface FormatterSelection {
    String format(List<DiffEntry> diff) throws Exception;
}
