package com.javamaster.controllers.auth;

import com.javamaster.entities.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserRole role;
}
