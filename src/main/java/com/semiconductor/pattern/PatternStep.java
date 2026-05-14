package com.semiconductor.pattern;

/**
 * A single step in a test pattern.
 */
public interface PatternStep {
    
    /**
     * Execute this step on the given site.
     */
    void execute(int siteId);
}
