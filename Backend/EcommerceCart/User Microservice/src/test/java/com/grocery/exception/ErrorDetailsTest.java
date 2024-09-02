package com.grocery.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ErrorDetailsTest {
    
    private ErrorDetails errorDetails;
    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    public void setUp() {
        errorDetails = new ErrorDetails("Error", "Detail message", now);
    }

    @Test
    public void testGetError() {
        assertEquals("Error", errorDetails.getError());
    }

    @Test
    public void testSetError() {
        errorDetails.setError("New Error");
        assertEquals("New Error", errorDetails.getError());
    }

    @Test
    public void testGetDetails() {
        assertEquals("Detail message", errorDetails.getDetails());
    }

    @Test
    public void testSetDetails() {
        errorDetails.setDetails("New Detail");
        assertEquals("New Detail", errorDetails.getDetails());
    }

    @Test
    public void testGetTimestamp() {
        assertEquals(now, errorDetails.getTimestamp());
    }

    @Test
    public void testSetTimestamp() {
        LocalDateTime newTime = LocalDateTime.now();
        errorDetails.setTimestamp(newTime);
        assertEquals(newTime, errorDetails.getTimestamp());
    }
}
