package app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Component
public class MemoryMonitor {
    private static final Logger log = LoggerFactory.getLogger(MemoryMonitor.class);
    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    @Scheduled(fixedRate = 60000) // Run every minute
    public void logMemoryUsage() {
        final MemoryUsage heapMemoryUsage = this.memoryMXBean.getHeapMemoryUsage();
        final MemoryUsage nonHeapMemoryUsage = this.memoryMXBean.getNonHeapMemoryUsage();

        MemoryMonitor.log.info("Heap Memory Usage:");
        MemoryMonitor.log.info("Used: {} MB", heapMemoryUsage.getUsed() / (1024 * 1024));
        MemoryMonitor.log.info("Committed: {} MB", heapMemoryUsage.getCommitted() / (1024 * 1024));
        MemoryMonitor.log.info("Max: {} MB", heapMemoryUsage.getMax() / (1024 * 1024));

        MemoryMonitor.log.info("Non-Heap Memory Usage:");
        MemoryMonitor.log.info("Used: {} MB", nonHeapMemoryUsage.getUsed() / (1024 * 1024));
        MemoryMonitor.log.info("Committed: {} MB", nonHeapMemoryUsage.getCommitted() / (1024 * 1024));
        MemoryMonitor.log.info("Max: {} MB", nonHeapMemoryUsage.getMax() / (1024 * 1024));

        // Optional: Add warning if memory usage is high
        if ((double) heapMemoryUsage.getUsed() / heapMemoryUsage.getMax() > 0.75) {
            MemoryMonitor.log.warn("High memory usage detected! Over 75% of heap used.");
        }
    }

    public void forceGarbageCollection() {
        MemoryMonitor.log.info("Forcing Garbage Collection");
        System.gc();
    }
} 