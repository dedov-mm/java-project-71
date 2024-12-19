package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;
import hexlet.code.FormatterSelection;

import java.util.List;

public class JsonFormatter implements FormatterSelection {
    @Override
    public final String format(List<DiffEntry> diff) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(diff);
    }
}
