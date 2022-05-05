package com.javamaster.service;

import com.javamaster.mother.UserMother;
import com.javamaster.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void shouldFindUserByEmailAndPasswordWhenUserExists() {
        Mockito.when(userRepository.findByEmail("employee1_mogilev@yopmail.com"))
                .thenReturn(UserMother.create().build());
        Mockito.when(passwordEncoder.matches("employee1", "employee1")).thenReturn(true);
        Assertions.assertNotNull(userService.findByEmailAndPassword("employee1_mogilev@yopmail.com", "employee1"));
    }
}