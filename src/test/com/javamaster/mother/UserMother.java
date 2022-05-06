package com.javamaster.mother;

import com.javamaster.entity.User;
import com.javamaster.entity.enums.UserRole;

public class UserMother {
    public static User.UserBuilder<?, ?> create() {
        return User.builder()
                .id(8)
                .name("user8")
                .email("employee8_mogilev@yopmail.com")
                .password("employee8")
                .role(UserRole.EMPLOYEE);
    }

    public static User.UserBuilder<?, ?> createIvan() {
        return User.builder()
                .id(null)
                .email("ivan@yopmail.com")
                .name("Ivan")
                .password("ivan")
                .role(UserRole.MANAGER);
    }

    public static User.UserBuilder<?, ?> createPetr() {
        return User.builder()
                .id(null)
                .email("petr@yopmail.com")
                .name("Petr")
                .password("petr")
                .role(UserRole.ENGINEER);
    }
}
