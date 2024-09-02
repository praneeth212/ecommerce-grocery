package com.grocery.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JwtConstantTest {

    @Test
    public void testSecretKey() {
        String expectedSecretKey = "wpembytrwcvnryxksdbqwjebruyGHyudqgwveytrtrCSnwifoesarjbwe";
        assertEquals(expectedSecretKey, JwtConstant.SECRET_KEY, "The secret key should match the expected value");
    }

    @Test
    public void testJwtHeader() {
        String expectedJwtHeader = "Authorization";
        assertEquals(expectedJwtHeader, JwtConstant.JWT_HEADER, "The JWT header should match the expected value");
    }
}
