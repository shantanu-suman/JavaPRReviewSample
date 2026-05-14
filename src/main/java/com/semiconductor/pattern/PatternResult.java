package com.semiconductor.pattern;

import java.util.List;

/**
 * Result of pattern execution.
 */
public class PatternResult {
    
    private final String patternName;
    private final List<SiteResult> siteResults;
    
    public PatternResult(String patternName, List<SiteResult> siteResults) {
        this.patternName = patternName;
        this.siteResults = siteResults;
    }
    
    public String getPatternName() {
        return patternName;
    }
    
    public List<SiteResult> getSiteResults() {
        return siteResults;
    }
    
    public boolean isAllPassed() {
        return siteResults.stream().allMatch(SiteResult::isPassed);
    }
}
