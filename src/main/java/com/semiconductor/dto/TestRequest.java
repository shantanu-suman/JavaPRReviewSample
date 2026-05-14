package com.semiconductor.dto;

/**
 * Test execution request DTO.
 */
public class TestRequest {
    
    private String testName;
    private String pattern;
    private int siteCount;
    
    public TestRequest() {}
    
    public TestRequest(String testName, String pattern, int siteCount) {
        this.testName = testName;
        this.pattern = pattern;
        this.siteCount = siteCount;
    }
    
    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }
    
    public String getPattern() { return pattern; }
    public void setPattern(String pattern) { this.pattern = pattern; }
    
    public int getSiteCount() { return siteCount; }
    public void setSiteCount(int siteCount) { this.siteCount = siteCount; }
}
