package pl.allegro.tech.hermes.common.util;

public class ValueWithTimestamp <T> {

    private final T value;
    private final long timestamp;

    private ValueWithTimestamp(T value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public T getValue() {
        return value;
    }

    public long age() {
        return System.currentTimeMillis() - timestamp;
    }

    public static <T> ValueWithTimestamp getNow(T value) {
        return new ValueWithTimestamp(value, System.currentTimeMillis());
    }
}
