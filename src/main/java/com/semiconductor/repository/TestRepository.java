package com.semiconductor.repository;

import org.springframework.stereotype.Repository;
import com.semiconductor.dto.TestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TestRepository - Data access for test records.
 */
@Repository
public class TestRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(TestRepository.class);
    
    private final Map<String, TestRecord> records = new ConcurrentHashMap<>();
    
    public void saveTestRecord(String testId, String testName) {
        records.put(testId, new TestRecord(testId, testName, "RUNNING"));
        logger.debug("Saved test record: {}", testId);
    }
    
    public void updateTestResult(String testId, boolean passed) {
        TestRecord record = records.get(testId);
        if (record != null) {
            record.setStatus(passed ? "PASSED" : "FAILED");
            logger.debug("Updated test record: {} -> {}", testId, record.getStatus());
        }
    }
    
    public TestResponse getTestResponse(String testId) {
        TestRecord record = records.get(testId);
        if (record == null) {
            return new TestResponse(testId, "NOT_FOUND");
        }
        return new TestResponse(testId, record.getStatus());
    }
    
    private static class TestRecord {
        private final String testId;
        private final String testName;
        private String status;
        
        TestRecord(String testId, String testName, String status) {
            this.testId = testId;
            this.testName = testName;
            this.status = status;
        }
        
        String getStatus() { return status; }
        void setStatus(String status) { this.status = status; }
    }
}
