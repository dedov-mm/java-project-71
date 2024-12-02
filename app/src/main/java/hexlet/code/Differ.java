package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Differ {
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Path path1 = Paths.get(filepath1).toAbsolutePath();
        Path path2 = Paths.get(filepath2).toAbsolutePath();

        if (!Files.exists(path1) || !Files.exists(path2)) {
            throw new IOException("One or both files do not exist.\nPaths:\n" + path1 + "\n" + path2);
        }

        String content1 = Files.readString(path1);
        String content2 = Files.readString(path2);

        Map<String, Object> map1 = Parser.parse(content1);
        Map<String, Object> map2 = Parser.parse(content2);

        List<DiffEntry> diff = DiffGenerator.generateDiff(map1, map2);
        FormatterSelection formatter = Formatter.getFormatter(format);

        return formatter.format(diff);
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }
}
