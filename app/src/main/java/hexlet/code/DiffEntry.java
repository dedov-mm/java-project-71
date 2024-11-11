package hexlet.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DiffEntry {
    private final String key;
    private final Object oldValue;
    private final Object newValue;
    private final DiffType type;

    public enum DiffType {
        ADDED, REMOVED, CHANGED, UNCHANGED
    }

    @JsonCreator
    public DiffEntry(@JsonProperty("key") String key,
                     @JsonProperty("oldValue") Object oldValue,
                     @JsonProperty("newValue") Object newValue,
                     @JsonProperty("type") DiffType type) {
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
