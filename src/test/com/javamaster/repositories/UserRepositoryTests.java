package com.javamaster.repositories;

import com.javamaster.entities.User;
import com.javamaster.entities.enums.UserRole;
import com.javamaster.utils.mothers.UserMother;
import com.javamaster.repositories.abstracts.DatabaseIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTests extends DatabaseIntegrationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindByEmailWhenUserExists() {
        User expected = UserMother.create().id(null).build();

        saveAndFlush(expected);

        User actual = userRepository.findByEmail("employee8_mogilev@yopmail.com");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnOnlyManagersWhenSearchManagers() {
        saveAndFlush(UserMother.createIvan().build(),
                UserMother.createPetr().build()
        );
        List<User> expected = List.of(
                UserMother.create().id(4).name("manager1").email("manager1_mogilev@yopmail.com").role(UserRole.MANAGER)
                        .password("$2a$10$eiOYSMMIyPolkhiL.kK8meRhjNfDHtcI2cyKOJy2L406WmPNeEnfm").build(),
                UserMother.create().id(5).name("manager2").email("manager2_mogilev@yopmail.com").role(UserRole.MANAGER)
                        .password("$2a$10$n/46njNnDzOSk63lKKsrmeJwRowhryWkf/O.JTnLw9SqK.X7WDB.O").build(),
                UserMother.createIvan().id(1000).build()
        );
        List<User> actual = userRepository.findAllByRole(UserRole.MANAGER);
        assertEquals(expected, actual);
    }
}