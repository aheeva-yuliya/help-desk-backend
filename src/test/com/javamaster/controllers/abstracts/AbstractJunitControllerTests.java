package com.javamaster.controllers.abstracts;

import com.javamaster.auth.TestUserDetailsService;
import com.javamaster.config.jwt.JwtProvider;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import(TestUserDetailsService.class)
public class AbstractJunitControllerTests extends AbstractControllerTests {
    @MockBean
    protected JwtProvider jwtProvider;
}
