package com.javamaster.controllers.abstracts;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractControllerTests {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mvc;

    @SneakyThrows
    protected ResultActions sendData(final HttpMethod method,
                                     final String url,
                                     final Object object) {
        final var request = MockMvcRequestBuilders.request(method, url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(object))
                .with(csrf());
        return mvc.perform(request).andExpect(status().isOk());
    }

    @SneakyThrows
    protected <T> T getData(final String url,
                            final TypeReference<T> type) {
        final String actualString = mvc.perform(MockMvcRequestBuilders.get(url)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(actualString, type);
    }

    @SneakyThrows
    protected ResultActions deleteData(final HttpMethod method,
                                       final String url) {
        return mvc.perform(MockMvcRequestBuilders.request(method, url)
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    @SneakyThrows
    protected ResultActions testForbidden(final HttpMethod method,
                                          final String url,
                                          final Object object) {
        final var request = MockMvcRequestBuilders.request(method, url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(object))
                .with(csrf());
        return mvc.perform(request).andExpect(status().isForbidden());
    }
}
