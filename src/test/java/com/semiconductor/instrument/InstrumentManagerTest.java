package com.semiconductor.instrument;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for InstrumentManager.
 */
public class InstrumentManagerTest {
    
    private InstrumentManager manager;
    
    @BeforeEach
    void setUp() {
        manager = new InstrumentManager("localhost:5000");
    }
    
    @AfterEach
    void tearDown() {
        if (manager != null) {
            manager.closeAllSessions();
        }
    }
    
    @Test
    void testOpenSession() {
        InstrumentSession session = manager.openSession("INST001");
        
        assertNotNull(session);
        assertTrue(session.isConnected());
        assertEquals(1, manager.getActiveSessionCount());
    }
    
    @Test
    void testCloseSession() {
        manager.openSession("INST001");
        assertEquals(1, manager.getActiveSessionCount());
        
        manager.closeSession("INST001");
        assertEquals(0, manager.getActiveSessionCount());
    }
    
    @Test
    void testDuplicateSession() {
        manager.openSession("INST001");
        
        assertThrows(IllegalStateException.class, () -> {
            manager.openSession("INST001");
        });
    }
    
    @Test
    void testCloseAllSessions() {
        manager.openSession("INST001");
        manager.openSession("INST002");
        manager.openSession("INST003");
        
        assertEquals(3, manager.getActiveSessionCount());
        
        manager.closeAllSessions();
        assertEquals(0, manager.getActiveSessionCount());
    }
}
