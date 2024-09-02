package com.grocery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.grocery.domain.USER_ROLE;
import com.grocery.modal.User;
import com.grocery.repository.UserRepository;

class CustomUserDetailsTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "test@example.com";
        User user = new User();
        user.setEmail(username);
        user.setPassword("password");
        user.setRole(USER_ROLE.ROLE_CUSTOMER);

        when(userRepository.findByEmail(username)).thenReturn(user);

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.getAuthorities()).hasSize(1);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo(USER_ROLE.ROLE_CUSTOMER.toString());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "notfound@example.com";

        when(userRepository.findByEmail(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> customUserDetails.loadUserByUsername(username));
    }

    @Test
    void testLoadUserByUsername_UserWithoutRole() {
        String username = "test@example.com";
        User user = new User();
        user.setEmail(username);
        user.setPassword("password");
        user.setRole(null); // No role assigned

        when(userRepository.findByEmail(username)).thenReturn(user);

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.getAuthorities()).hasSize(1);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo(USER_ROLE.ROLE_CUSTOMER.toString());
    }
}
