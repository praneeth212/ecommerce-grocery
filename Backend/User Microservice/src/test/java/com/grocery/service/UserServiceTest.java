package com.grocery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.grocery.config.JwtProvider;
import com.grocery.exception.UserException;
import com.grocery.modal.PasswordResetToken;
import com.grocery.modal.User;
import com.grocery.repository.AddressRepository;
import com.grocery.repository.PasswordResetTokenRepository;
import com.grocery.repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserProfileByJwt() throws UserException {
        String jwt = "some-jwt-token";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(jwtProvider.getEmailFromJwtToken(jwt)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.findUserProfileByJwt(jwt);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    void testFindUserById() throws UserException {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findUserById(userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
    }

    @Test
    void testUpdatePassword() {
        User user = new User();
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        userService.updatePassword(user, newPassword);

        verify(userRepository, times(1)).save(user);
        assertThat(user.getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    void testSendPasswordResetEmail() {
        User user = new User();
        user.setEmail("test@example.com");

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);

        when(passwordResetTokenRepository.findByUser(user)).thenReturn(passwordResetToken);
        when(passwordResetTokenRepository.save(any(PasswordResetToken.class))).thenReturn(passwordResetToken);

        userService.sendPasswordResetEmail(user);

        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));
//        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testFindUserByEmail() throws UserException {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.findUserByEmail(email);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    void testUpdateUserProfile() throws UserException {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Doe");
        updatedUser.setMobile("1234567890");

        User existingUser = new User();
        existingUser.setId(1L);

        when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUserProfile(updatedUser);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getMobile()).isEqualTo("1234567890");
    }
}
