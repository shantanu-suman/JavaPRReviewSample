package com.semiconductor.pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.semiconductor.instrument.InstrumentSession;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

/**
 * PatternExecutor - Executes test patterns on semiconductor devices.
 * 
 * Handles multisite execution with precise pattern timing.
 */
public class PatternExecutor {
    
    private static final Logger logger = LoggerFactory.getLogger(PatternExecutor.class);
    
    private final ExecutorService executor;
    private final int siteCount;
    private final List<InstrumentSession> sessions;
    
    public PatternExecutor(int siteCount) {
        this.siteCount = siteCount;
        this.executor = Executors.newFixedThreadPool(siteCount);
        this.sessions = new ArrayList<>();
        
        logger.info("PatternExecutor initialized for {} sites", siteCount);
    }
    
    /**
     * Execute a test pattern across all sites.
     */
    public PatternResult executePattern(TestPattern pattern) {
        logger.info("Executing pattern: {} on {} sites", pattern.getName(), siteCount);
        
        List<Future<SiteResult>> futures = new ArrayList<>();
        
        for (int site = 0; site < siteCount; site++) {
            final int siteId = site;
            futures.add(executor.submit(() -> executeSitePattern(siteId, pattern)));
        }
        
        // Collect results
        List<SiteResult> results = new ArrayList<>();
        for (Future<SiteResult> future : futures) {
            try {
                results.add(future.get());
            } catch (Exception e) {
                logger.error("Site execution failed", e);
                results.add(new SiteResult(-1, false, e.getMessage()));
            }
        }
        
        return new PatternResult(pattern.getName(), results);
    }
    
    private SiteResult executeSitePattern(int siteId, TestPattern pattern) {
        logger.debug("Site {} executing pattern {}", siteId, pattern.getName());
        
        try {
            // Execute pattern steps
            for (PatternStep step : pattern.getSteps()) {
                step.execute(siteId);
            }
            
            return new SiteResult(siteId, true, "Success");
        } catch (Exception e) {
            logger.error("Site {} pattern execution failed", siteId, e);
            return new SiteResult(siteId, false, e.getMessage());
        }
    }
    
    /**
     * Shutdown the executor.
     */
    public void shutdown() {
        executor.shutdown();
        
        // Close all sessions
        for (InstrumentSession session : sessions) {
            try {
                session.close();
            } catch (Exception e) {
                logger.error("Failed to close session", e);
            }
        }
        
        logger.info("PatternExecutor shutdown complete");
    }
}
