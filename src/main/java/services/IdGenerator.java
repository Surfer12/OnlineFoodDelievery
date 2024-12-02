package services;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final AtomicLong counter = new AtomicLong(0);

    public static Long generateId() {
        return IdGenerator.counter.incrementAndGet();
    }
}
