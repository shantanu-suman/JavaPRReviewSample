package com.semiconductor.instrument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * InstrumentManager - Manages hardware instrument connections.
 * 
 * Provides centralized instrument session management.
 */
public class InstrumentManager {
    
    private static final Logger logger = LoggerFactory.getLogger(InstrumentManager.class);
    
    private final Map<String, InstrumentSession> activeSessions;
    private final String connectionString;
    
    public InstrumentManager(String connectionString) {
        this.connectionString = connectionString;
        this.activeSessions = new ConcurrentHashMap<>();
        
        logger.info("InstrumentManager initialized with connection: {}", connectionString);
    }
    
    /**
     * Open a new instrument session.
     */
    public InstrumentSession openSession(String instrumentId) {
        logger.info("Opening session for instrument: {}", instrumentId);
        
        if (activeSessions.containsKey(instrumentId)) {
            throw new IllegalStateException("Session already exists for: " + instrumentId);
        }
        
        InstrumentSession session = new InstrumentSession(instrumentId, connectionString);
        session.connect();
        activeSessions.put(instrumentId, session);
        
        return session;
    }
    
    /**
     * Close an instrument session.
     */
    public void closeSession(String instrumentId) {
        InstrumentSession session = activeSessions.remove(instrumentId);
        
        if (session != null) {
            try {
                session.close();
                logger.info("Closed session for instrument: {}", instrumentId);
            } catch (Exception e) {
                logger.error("Error closing session for: {}", instrumentId, e);
            }
        }
    }
    
    /**
     * Get an existing session.
     */
    public InstrumentSession getSession(String instrumentId) {
        return activeSessions.get(instrumentId);
    }
    
    /**
     * Close all active sessions.
     */
    public void closeAllSessions() {
        logger.info("Closing all {} active sessions", activeSessions.size());
        
        for (String instrumentId : activeSessions.keySet()) {
            closeSession(instrumentId);
        }
    }
    
    /**
     * Get count of active sessions.
     */
    public int getActiveSessionCount() {
        return activeSessions.size();
    }
}
