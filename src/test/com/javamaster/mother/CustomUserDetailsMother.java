package com.javamaster.mother;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.entity.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class CustomUserDetailsMother {
    public static CustomUserDetails.CustomUserDetailsBuilder<?, ?> create() {
        return CustomUserDetails.builder()
                .id(1)
                .name("user1")
                .login("employee1_mogilev@yopmail.com")
                .password("employee1")
                .userRole(UserRole.EMPLOYEE)
                .grantedAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
    }
}
