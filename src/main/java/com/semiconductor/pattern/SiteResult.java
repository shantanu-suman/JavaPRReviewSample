package com.semiconductor.pattern;

/**
 * Result from a single site execution.
 */
public class SiteResult {
    
    private final int siteId;
    private final boolean passed;
    private final String message;
    
    public SiteResult(int siteId, boolean passed, String message) {
        this.siteId = siteId;
        this.passed = passed;
        this.message = message;
    }
    
    public int getSiteId() {
        return siteId;
    }
    
    public boolean isPassed() {
        return passed;
    }
    
    public String getMessage() {
        return message;
    }
}
