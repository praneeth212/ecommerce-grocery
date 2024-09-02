package com.grocery.modal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;

public class PasswordResetTokenTest {

    private PasswordResetToken passwordResetToken;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken("123456");
    }

    @Test
    void testGetterSetter() {
        // Test the id
        passwordResetToken.setId(1);
        assertEquals(1, passwordResetToken.getId());

        // Test the token
        String token = "abcdef";
        passwordResetToken.setToken(token);
        assertEquals(token, passwordResetToken.getToken());

        // Test the user
        assertNotNull(passwordResetToken.getUser());
        assertEquals(user, passwordResetToken.getUser());

        // Test the expiry date
        Date now = new Date();
        passwordResetToken.setExpiryDate(now);
        assertEquals(now, passwordResetToken.getExpiryDate());
    }

    @Test
    void testIsExpiredTrue() {
        // Set the expiry date to one day before the current date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // Subtract 1 day from current date
        Date expiredDate = calendar.getTime();
        passwordResetToken.setExpiryDate(expiredDate);
        
        assertTrue(passwordResetToken.isExpired());
    }

    @Test
    void testIsExpiredFalse() {
        // Set the expiry date to one day after the current date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // Add 1 day to current date
        Date notExpiredDate = calendar.getTime();
        passwordResetToken.setExpiryDate(notExpiredDate);

        assertFalse(passwordResetToken.isExpired());
    }
}
