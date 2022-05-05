package com.javamaster.mother;

import com.javamaster.entity.User;
import com.javamaster.entity.enums.UserRole;

public class UserMother {
    public static User.UserBuilder<?, ?> create() {
        return User.builder()
                .id(1)
                .name("user1")
                .email("employee1_mogilev@yopmail.com")
                .password("employee1")
                .role(UserRole.EMPLOYEE);
    }
}
