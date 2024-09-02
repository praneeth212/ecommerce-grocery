package com.grocery.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginRequestTest {

    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() {
        loginRequest = new LoginRequest();
    }

    @Test
    public void testDefaultConstructor() {
        LoginRequest defaultLoginRequest = new LoginRequest();
        assertNotNull(defaultLoginRequest);
    }

    @Test
    public void testParameterizedConstructor() {
        LoginRequest paramLoginRequest = new LoginRequest("user@example.com", "password123");
        assertEquals("user@example.com", paramLoginRequest.getEmail());
        assertEquals("password123", paramLoginRequest.getPassword());
    }

    @Test
    public void testGettersAndSetters() {
        loginRequest.setEmail("user@example.com");
        assertEquals("user@example.com", loginRequest.getEmail());

        loginRequest.setPassword("password123");
        assertEquals("password123", loginRequest.getPassword());
    }
}
