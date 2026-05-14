package com.semiconductor.timing;

import java.util.List;

/**
 * Represents a sequence of timing events for test execution.
 */
public class TimingSequence {
    
    private final String name;
    private final List<TimingEvent> events;
    
    public TimingSequence(String name, List<TimingEvent> events) {
        this.name = name;
        this.events = events;
    }
    
    public String getName() {
        return name;
    }
    
    public List<TimingEvent> getEvents() {
        return events;
    }
}
