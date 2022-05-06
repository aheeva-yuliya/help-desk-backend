package com.javamaster.repository;

import com.javamaster.entity.User;
import com.javamaster.entity.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTests extends DatabaseIntegrationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindByEmailWhenUserExists() {
        User expected = User.builder()
                .id(null)
                .name("user8")
                .email("employee8_mogilev@yopmail.com")
                .password("employee8")
                .role(UserRole.EMPLOYEE)
                .build();
        saveAndFlush(expected);
        expected.setId(8);
        User actual = userRepository.findByEmail("employee8_mogilev@yopmail.com");
        Assertions.assertEquals(expected, actual);
    }
//
//    @Test
//    void findAllByRole() {
//    }
}