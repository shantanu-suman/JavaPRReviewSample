package com.semiconductor.timing;

/**
 * Represents a single timing event in a sequence.
 */
public interface TimingEvent {
    
    /**
     * Get the delay in nanoseconds before this event executes.
     */
    long getDelayNs();
    
    /**
     * Execute the timing event.
     */
    void execute();
}
