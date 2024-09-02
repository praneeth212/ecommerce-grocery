package com.grocery.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.grocery.domain.USER_ROLE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthResponseTest {

    private AuthResponse authResponse;

    @BeforeEach
    public void setUp() {
        authResponse = new AuthResponse();
    }

    @Test
    public void testDefaultConstructor() {
        AuthResponse defaultAuthResponse = new AuthResponse();
        assertNotNull(defaultAuthResponse);
    }

    @Test
    public void testParameterizedConstructor() {
        AuthResponse paramAuthResponse = new AuthResponse("Success", "jwtToken", USER_ROLE.ROLE_ADMIN);
        assertEquals("Success", paramAuthResponse.getMessage());
        assertEquals("jwtToken", paramAuthResponse.getJwt());
        assertEquals(USER_ROLE.ROLE_ADMIN, paramAuthResponse.getRole());
    }

    @Test
    public void testGettersAndSetters() {
        authResponse.setMessage("Success");
        assertEquals("Success", authResponse.getMessage());

        authResponse.setJwt("jwtToken");
        assertEquals("jwtToken", authResponse.getJwt());

        authResponse.setRole(USER_ROLE.ROLE_ADMIN);
        assertEquals(USER_ROLE.ROLE_ADMIN, authResponse.getRole());
    }

    @Test
    public void testToString() {
        authResponse.setMessage("Success");
        authResponse.setJwt("jwtToken");
        authResponse.setRole(USER_ROLE.ROLE_ADMIN);

        String expectedString = "AuthResponse [message=Success, jwt=jwtToken, role=ROLE_ADMIN]";
        assertEquals(expectedString, authResponse.toString());
    }
}
