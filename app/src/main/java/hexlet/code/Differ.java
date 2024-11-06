package hexlet.code;

import java.nio.file.Path;
import java.util.List;

public class Differ {
    public static String generate(Path path1, Path path2, String format) throws Exception {
        List<DiffEntry> diff = DiffGenerator.generateDiff(path1, path2);
        Formatter formatter = Formatter.getFormatter(format);
        return formatter.format(diff);
    }
}
