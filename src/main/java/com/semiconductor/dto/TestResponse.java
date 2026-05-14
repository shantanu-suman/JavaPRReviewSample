package com.semiconductor.dto;

/**
 * Test execution response DTO.
 */
public class TestResponse {
    
    private String testId;
    private String status;
    
    public TestResponse() {}
    
    public TestResponse(String testId, String status) {
        this.testId = testId;
        this.status = status;
    }
    
    public String getTestId() { return testId; }
    public void setTestId(String testId) { this.testId = testId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
