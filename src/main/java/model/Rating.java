package model;

import java.time.LocalDateTime;

public class Rating {
    private final int value;
    private final LocalDateTime timestamp;

    public Rating(int value) {
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }

    public int getValue() {
        return value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
