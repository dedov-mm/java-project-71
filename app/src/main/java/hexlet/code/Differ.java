package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Differ {
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Path path1 = Paths.get(filepath1).toAbsolutePath();
        Path path2 = Paths.get(filepath2).toAbsolutePath();

        if (!Files.exists(path1) || !Files.exists(path2)) {
            throw new IOException("One or both files do not exist.");
        }

        List<DiffEntry> diff = DiffGenerator.generateDiff(path1, path2);
        FormatterSelection formatter = Formatter.getFormatter(format);
        return formatter.format(diff);
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }
}
