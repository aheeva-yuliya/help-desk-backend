package com.javamaster.config.jwt;

import com.javamaster.entity.User;
import com.javamaster.service.UserService;
import com.javamaster.service.adapters.UserServiceAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServiceAdapter userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userService.findByEmail(email);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}
