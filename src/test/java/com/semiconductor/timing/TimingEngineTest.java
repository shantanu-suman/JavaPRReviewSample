package com.semiconductor.timing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tests for TimingEngine.
 */
public class TimingEngineTest {
    
    private TimingEngine engine;
    
    @BeforeEach
    void setUp() {
        engine = new TimingEngine(2, 1000);
    }
    
    @AfterEach
    void tearDown() {
        if (engine != null) {
            engine.shutdown();
        }
    }
    
    @Test
    void testEngineInitialization() {
        assertNotNull(engine);
        assertFalse(engine.isRunning());
    }
    
    @Test
    void testSequenceExecution() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        
        TimingEvent event = new TimingEvent() {
            @Override
            public long getDelayNs() {
                return 1000000; // 1ms
            }
            
            @Override
            public void execute() {
                counter.incrementAndGet();
            }
        };
        
        TimingSequence sequence = new TimingSequence("test", Arrays.asList(event, event, event));
        
        engine.startSequence(sequence);
        
        // Wait for completion
        Thread.sleep(100);
        
        assertEquals(3, counter.get());
    }
    
    @Test
    void testStopSequence() {
        TimingEvent longEvent = new TimingEvent() {
            @Override
            public long getDelayNs() {
                return 1000000000; // 1 second
            }
            
            @Override
            public void execute() {
                // Do nothing
            }
        };
        
        TimingSequence sequence = new TimingSequence("long", Arrays.asList(longEvent));
        
        engine.startSequence(sequence);
        assertTrue(engine.isRunning());
        
        engine.stopSequence();
        assertFalse(engine.isRunning());
    }
}
