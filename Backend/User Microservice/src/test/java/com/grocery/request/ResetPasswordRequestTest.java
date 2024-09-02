package com.grocery.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResetPasswordRequestTest {

    private ResetPasswordRequest resetPasswordRequest;

    @BeforeEach
    public void setUp() {
        resetPasswordRequest = new ResetPasswordRequest();
    }

    @Test
    public void testDefaultConstructor() {
        ResetPasswordRequest defaultResetPasswordRequest = new ResetPasswordRequest();
        assertNotNull(defaultResetPasswordRequest);
    }

    @Test
    public void testParameterizedConstructor() {
        ResetPasswordRequest paramResetPasswordRequest = new ResetPasswordRequest("newPassword", "token123");
        assertEquals("newPassword", paramResetPasswordRequest.getPassword());
        assertEquals("token123", paramResetPasswordRequest.getToken());
    }

    @Test
    public void testGettersAndSetters() {
        resetPasswordRequest.setPassword("newPassword");
        assertEquals("newPassword", resetPasswordRequest.getPassword());

        resetPasswordRequest.setToken("token123");
        assertEquals("token123", resetPasswordRequest.getToken());
    }

    @Test
    public void testToString() {
        resetPasswordRequest.setPassword("newPassword");
        resetPasswordRequest.setToken("token123");

        String expectedString = "ResetPasswordRequest [password=newPassword, token=token123]";
        assertEquals(expectedString, resetPasswordRequest.toString());
    }
}
