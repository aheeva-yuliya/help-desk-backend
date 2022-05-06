package com.javamaster.controller;

import com.javamaster.auth.TestUserDetailsService;
import com.javamaster.config.jwt.JwtFilter;
import com.javamaster.config.jwt.JwtProvider;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;

@Import(TestUserDetailsService.class)
public class AbstractJunitControllerTests extends AbstractControllerTests {
//    @MockBean
//    protected AuthenticationProvider authenticationProvider;
    @MockBean
    protected JwtProvider jwtProvider;
    @MockBean
    protected JwtFilter jwtFilter;
}
