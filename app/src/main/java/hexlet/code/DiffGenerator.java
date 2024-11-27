package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public class DiffGenerator {
    public static List<DiffEntry> generateDiff(Path path1, Path path2) throws Exception {
        if (!Files.exists(path1) || !Files.exists(path2)) {
            throw new IOException("Files do not exist");
        }

        Map<String, Object> map1 = Parser.parse(path1);
        Map<String, Object> map2 = Parser.parse(path2);

        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        List<DiffEntry> diff = new ArrayList<>();
        for (String key : allKeys) {
            if (!map1.containsKey(key)) {
                diff.add(new DiffEntry(key, null, map2.get(key), DiffEntry.DiffType.ADDED));
            } else if (!map2.containsKey(key)) {
                diff.add(new DiffEntry(key, map1.get(key), null, DiffEntry.DiffType.REMOVED));
            } else {
                Object value1 = map1.get(key);
                Object value2 = map2.get(key);
                if (Objects.equals(value1, value2)) {
                    diff.add(new DiffEntry(key, value1, value2, DiffEntry.DiffType.UNCHANGED));
                } else {
                    diff.add(new DiffEntry(key, value1, value2, DiffEntry.DiffType.CHANGED));
                }
            }
        }
        return diff;
    }
}
