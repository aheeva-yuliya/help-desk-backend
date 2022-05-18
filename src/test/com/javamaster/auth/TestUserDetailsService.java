package com.javamaster.auth;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.entities.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class TestUserDetailsService implements UserDetailsService {
    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new CustomUserDetails(
                1,
                "user1",
                "employee1_mogilev@yopmail.com",
                "employee1",
                UserRole.EMPLOYEE,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
        );
    }
}
