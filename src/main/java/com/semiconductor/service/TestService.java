package com.semiconductor.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.semiconductor.repository.TestRepository;
import com.semiconductor.dto.TestRequest;
import com.semiconductor.dto.TestResponse;
import com.semiconductor.pattern.PatternExecutor;
import com.semiconductor.pattern.TestPattern;
import com.semiconductor.pattern.PatternResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

/**
 * TestService - Business logic for test execution.
 */
@Service
public class TestService {
    
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);
    
    private final TestRepository testRepository;
    private final PatternExecutor patternExecutor;
    
    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
        this.patternExecutor = new PatternExecutor(4); // 4 sites
    }
    
    @Transactional
    public TestResponse executeTest(TestRequest request) {
        String testId = UUID.randomUUID().toString();
        logger.info("Starting test execution: {}", testId);
        
        // Store test record
        testRepository.saveTestRecord(testId, request.getTestName());
        
        // Build pattern
        TestPattern pattern = buildPattern(request);
        
        // Execute pattern
        PatternResult result = patternExecutor.executePattern(pattern);
        
        // Update record
        testRepository.updateTestResult(testId, result.isAllPassed());
        
        return new TestResponse(testId, result.isAllPassed() ? "PASSED" : "FAILED");
    }
    
    public TestResponse getTestStatus(String testId) {
        return testRepository.getTestResponse(testId);
    }
    
    public void cancelTest(String testId) {
        logger.info("Cancelling test: {}", testId);
        testRepository.updateTestResult(testId, false);
    }
    
    private TestPattern buildPattern(TestRequest request) {
        // Build pattern from request
        return new TestPattern(request.getTestName(), java.util.Collections.emptyList());
    }
}
