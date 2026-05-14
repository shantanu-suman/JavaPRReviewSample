package com.semiconductor.timing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TimingEngine - Core timing control for semiconductor test execution.
 * 
 * This is a timing-critical module that controls test execution timing
 * with nanosecond precision requirements.
 */
public class TimingEngine {
    
    private static final Logger logger = LoggerFactory.getLogger(TimingEngine.class);
    
    private final ExecutorService executor;
    private volatile boolean running;
    private final long timingPrecisionNs;
    
    public TimingEngine(int threads, long precisionNs) {
        this.executor = Executors.newFixedThreadPool(threads);
        this.timingPrecisionNs = precisionNs;
        this.running = false;
        logger.info("TimingEngine initialized with {} threads, precision: {}ns", threads, precisionNs);
    }
    
    /**
     * Start timing sequence execution.
     */
    public void startSequence(TimingSequence sequence) {
        if (running) {
            throw new IllegalStateException("Timing sequence already running");
        }
        
        running = true;
        logger.info("Starting timing sequence: {}", sequence.getName());
        
        executor.submit(() -> {
            try {
                executeSequence(sequence);
            } catch (Exception e) {
                logger.error("Sequence execution failed", e);
            } finally {
                running = false;
            }
        });
    }
    
    private void executeSequence(TimingSequence sequence) {
        for (TimingEvent event : sequence.getEvents()) {
            if (!running) {
                break;
            }
            
            // Execute timing event with precise timing
            long targetTime = System.nanoTime() + event.getDelayNs();
            
            // Spin-wait for precise timing (no Thread.sleep!)
            while (System.nanoTime() < targetTime && running) {
                // Busy wait for precision
            }
            
            event.execute();
        }
    }
    
    /**
     * Stop current sequence execution.
     */
    public void stopSequence() {
        running = false;
        logger.info("Timing sequence stopped");
    }
    
    /**
     * Shutdown the timing engine.
     */
    public void shutdown() {
        running = false;
        executor.shutdown();
        
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        logger.info("TimingEngine shutdown complete");
    }
    
    public boolean isRunning() {
        return running;
    }
}
