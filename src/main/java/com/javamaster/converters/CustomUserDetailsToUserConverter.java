package com.javamaster.converters;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.entities.User;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsToUserConverter {
    public User convert(CustomUserDetails details) {
        return User.builder()
                .id(details.getId())
                .name(details.getName())
                .email(details.getLogin())
                .password(details.getPassword())
                .role(details.getUserRole())
                .build();
    }
}
