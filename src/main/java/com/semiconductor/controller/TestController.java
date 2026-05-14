package com.semiconductor.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.semiconductor.service.TestService;
import com.semiconductor.dto.TestRequest;
import com.semiconductor.dto.TestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestController - REST API for test execution.
 */
@RestController
@RequestMapping("/api/tests")
public class TestController {
    
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    private final TestService testService;
    
    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }
    
    @PostMapping("/execute")
    public TestResponse executeTest(@RequestBody TestRequest request) {
        logger.info("Received test execution request: {}", request.getTestName());
        return testService.executeTest(request);
    }
    
    @GetMapping("/status/{testId}")
    public TestResponse getTestStatus(@PathVariable String testId) {
        logger.info("Getting status for test: {}", testId);
        return testService.getTestStatus(testId);
    }
    
    @DeleteMapping("/{testId}")
    public void cancelTest(@PathVariable String testId) {
        logger.info("Cancelling test: {}", testId);
        testService.cancelTest(testId);
    }
}
