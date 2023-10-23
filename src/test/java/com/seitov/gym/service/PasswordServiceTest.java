package com.seitov.gym.service;

import com.seitov.gym.service.impl.PasswordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

    @Spy
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @InjectMocks
    private final PasswordService passwordService = new PasswordServiceImpl();

    @Test
    void passwordGeneration() {
        assertEquals(10, passwordService.generatePassword().length());
    }

    @Test
    void passwordEncoding() {
        String rawPassword = "testing123";
        String encodedPassword = passwordService.encodePassword(rawPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

}
