package com.grocery.service;

import com.grocery.domain.USER_ROLE;
import com.grocery.modal.User;
import com.grocery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class DataInitializationComponentTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializationComponent dataInitializationComponent;

    @Value("${SECRET}")
    private String secret = "superSecretPassword"; // Mocked value for SECRET

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the @Value injection
        dataInitializationComponent = new DataInitializationComponent(userRepository, passwordEncoder);
        ReflectionTestUtils.setField(dataInitializationComponent, "secret", secret);
    }

    @Test
    void testInitializeAdminUser_UserAlreadyExists() {
        String adminUsername = "invictus@brillio.com";
        when(userRepository.findByEmail(adminUsername)).thenReturn(new User());

        dataInitializationComponent.run();

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testInitializeAdminUser_UserDoesNotExist() {
        String adminUsername = "invictus@brillio.com";
        when(userRepository.findByEmail(adminUsername)).thenReturn(null);
        when(passwordEncoder.encode(secret)).thenReturn("encodedPassword");

        dataInitializationComponent.run();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo(adminUsername);
        assertThat(savedUser.getFirstName()).isEqualTo("Invictus");
        assertThat(savedUser.getLastName()).isEqualTo("Brillio");
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
        assertThat(savedUser.getRole()).isEqualTo(USER_ROLE.ROLE_ADMIN);
    }
}
