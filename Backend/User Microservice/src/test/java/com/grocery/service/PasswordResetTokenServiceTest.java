package com.grocery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.grocery.modal.PasswordResetToken;
import com.grocery.repository.PasswordResetTokenRepository;

class PasswordResetTokenServiceTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private PasswordResetTokenService passwordResetTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByToken() {
        String token = "testToken";
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        PasswordResetToken result = passwordResetTokenService.findByToken(token);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo(token);
    }

    @Test
    void testDelete() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken("testToken");

        doNothing().when(passwordResetTokenRepository).delete(passwordResetToken);

        passwordResetTokenService.delete(passwordResetToken);

        verify(passwordResetTokenRepository, times(1)).delete(passwordResetToken);
    }
}
