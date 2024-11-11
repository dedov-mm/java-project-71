package hexlet.code;

public class DiffEntry {
    private final String key;
    private final Object oldValue;
    private final Object newValue;
    private final DiffType type;

    public enum DiffType {
        ADDED, REMOVED, CHANGED, UNCHANGED
    }

    public DiffEntry(String key, Object oldValue, Object newValue, DiffType type) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.type = type;
    }

    public final String getKey() {
        return key;
    }

    public final Object getOldValue() {
        return oldValue;
    }

    public final Object getNewValue() {
        return newValue;
    }

    public final DiffType getType() {
        return type;
    }
}
