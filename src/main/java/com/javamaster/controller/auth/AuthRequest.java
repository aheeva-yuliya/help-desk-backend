package com.javamaster.controller.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AuthRequest {
    @NotEmpty(message = "Please fill out the required field.")
    @NotNull
    @Email
    private String email;
    @NotEmpty(message = "Please fill out the required field.")
    @NotNull
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20")
    private String password;
}
