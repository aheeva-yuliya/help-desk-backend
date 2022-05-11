package com.javamaster.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.javamaster.controller.abstracts.AbstractJunitControllerTests;
import com.javamaster.converter.HistoryToHistoryDtoConverter;
import com.javamaster.dto.HistoryResponseDto;
import com.javamaster.entity.History;
import com.javamaster.service.adapters.HistoryServiceAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(HistoryController.class)
public class HistoryControllerTests extends AbstractJunitControllerTests {
    @MockBean
    private HistoryServiceAdapter historyService;
    @MockBean
    private HistoryToHistoryDtoConverter converter;

    @Test
    @WithUserDetails("employee1_mogilev@yopmail.com")
    public void shouldGetAllByTicketIdWhenRequestByAuthorized() {
        when(historyService.getHistoryByTicketId(2L))
                .thenReturn(List.of(History.builder().build(), History.builder().build()));
        when(converter.convert(History.builder().build())).thenReturn(HistoryResponseDto.builder().build());
        List<HistoryResponseDto> expected = List.of(HistoryResponseDto.builder().build(), HistoryResponseDto.builder().build());
        List<HistoryResponseDto> actual = getData("/history/2", new TypeReference<>() {});
        Assertions.assertEquals(expected, actual);
    }
}