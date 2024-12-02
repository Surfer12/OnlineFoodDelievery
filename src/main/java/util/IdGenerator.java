package util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final AtomicLong counter = new AtomicLong(0);

    public Long generateId() {
        return IdGenerator.counter.incrementAndGet();
    }
}