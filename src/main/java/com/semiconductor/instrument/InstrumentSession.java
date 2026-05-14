package com.semiconductor.instrument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InstrumentSession - Represents a connection to a hardware instrument.
 * 
 * Must be properly closed to release hardware resources.
 */
public class InstrumentSession implements AutoCloseable {
    
    private static final Logger logger = LoggerFactory.getLogger(InstrumentSession.class);
    
    private final String instrumentId;
    private final String connectionString;
    private boolean connected;
    
    public InstrumentSession(String instrumentId, String connectionString) {
        this.instrumentId = instrumentId;
        this.connectionString = connectionString;
        this.connected = false;
    }
    
    /**
     * Connect to the instrument.
     */
    public void connect() {
        if (connected) {
            throw new IllegalStateException("Already connected");
        }
        
        logger.info("Connecting to instrument: {}", instrumentId);
        
        // Simulate connection
        connected = true;
        
        logger.info("Connected to instrument: {}", instrumentId);
    }
    
    /**
     * Send a command to the instrument.
     */
    public String sendCommand(String command) {
        if (!connected) {
            throw new IllegalStateException("Not connected");
        }
        
        logger.debug("Sending command to {}: {}", instrumentId, command);
        
        // Simulate command execution
        return "OK";
    }
    
    /**
     * Read data from the instrument.
     */
    public byte[] readData(int length) {
        if (!connected) {
            throw new IllegalStateException("Not connected");
        }
        
        logger.debug("Reading {} bytes from {}", length, instrumentId);
        
        // Simulate data read
        return new byte[length];
    }
    
    /**
     * Close the instrument session.
     */
    @Override
    public void close() {
        if (connected) {
            logger.info("Closing instrument session: {}", instrumentId);
            connected = false;
        }
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public String getInstrumentId() {
        return instrumentId;
    }
}
