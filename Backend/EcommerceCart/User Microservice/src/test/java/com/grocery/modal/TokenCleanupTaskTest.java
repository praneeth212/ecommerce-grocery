package com.grocery.modal;

import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.grocery.repository.PasswordResetTokenRepository;
import com.grocery.service.TokenCleaupTask;

@SpringBootTest
public class TokenCleanupTaskTest {

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @InjectMocks
    private TokenCleaupTask tokenCleanupTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cleanUpExpiredTokens_ShouldCallDeleteMethod() {
        // Execute
        tokenCleanupTask.cleanUpExpiredTokens();

        // Verify that deleteAllByExpiryDateBefore is called with any Date object
        verify(tokenRepository).deleteAllByExpiryDateBefore(any(Date.class));
    }
}
