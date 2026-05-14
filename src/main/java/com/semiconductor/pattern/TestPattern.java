package com.semiconductor.pattern;

import java.util.List;

/**
 * Represents a test pattern to be executed.
 */
public class TestPattern {
    
    private final String name;
    private final List<PatternStep> steps;
    
    public TestPattern(String name, List<PatternStep> steps) {
        this.name = name;
        this.steps = steps;
    }
    
    public String getName() {
        return name;
    }
    
    public List<PatternStep> getSteps() {
        return steps;
    }
}
