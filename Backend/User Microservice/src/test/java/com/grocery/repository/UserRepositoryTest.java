package com.grocery.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.grocery.modal.User;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(user);

        User foundUser = userRepository.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.get().getId());
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getId(), savedUser.getId());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        userRepository.deleteById(userId);

        verify(userRepository).deleteById(userId);
    }
}
