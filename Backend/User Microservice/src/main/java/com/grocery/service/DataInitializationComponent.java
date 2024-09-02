package com.grocery.service;

import com.grocery.domain.USER_ROLE;
import com.grocery.modal.User;
import com.grocery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    
    @Value("${SECRET}")
    private String secret;

    public DataInitializationComponent(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
    	
        String adminUsername = "invictus@brillio.com";

        if (userRepository.findByEmail(adminUsername) == null) {
            
        	User adminUser = new User();
            adminUser.setPassword(passwordEncoder.encode(secret));
            adminUser.setFirstName("Invictus");
            adminUser.setLastName("Brillio");
            adminUser.setEmail(adminUsername);
            adminUser.setRole(USER_ROLE.ROLE_ADMIN);
            userRepository.save(adminUser);
        }
    }

}
