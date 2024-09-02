package com.grocery.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.grocery.modal.PasswordResetToken;
import com.grocery.modal.User;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class PasswordResetTokenRepositoryTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private PasswordResetToken passwordResetToken;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        passwordResetToken = new PasswordResetToken();
        passwordResetToken.setId(1);
        passwordResetToken.setToken("test-token");
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(new Date());
    }

    @Test
    void testFindByToken() {
        String token = "test-token";
        when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        PasswordResetToken foundToken = passwordResetTokenRepository.findByToken(token);

        assertNotNull(foundToken);
        assertEquals(token, foundToken.getToken());
        assertEquals(passwordResetToken.getId(), foundToken.getId());
    }

    @Test
    void testFindByUser() {
        when(passwordResetTokenRepository.findByUser(user)).thenReturn(passwordResetToken);

        PasswordResetToken foundToken = passwordResetTokenRepository.findByUser(user);

        assertNotNull(foundToken);
        assertEquals(user, foundToken.getUser());
        assertEquals(passwordResetToken.getId(), foundToken.getId());
    }

    @Test
    void testDeleteAllByExpiryDateBefore() {
        Date now = new Date();
        passwordResetTokenRepository.deleteAllByExpiryDateBefore(now);

        ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
        verify(passwordResetTokenRepository).deleteAllByExpiryDateBefore(dateCaptor.capture());

        assertEquals(now, dateCaptor.getValue());
    }
}
