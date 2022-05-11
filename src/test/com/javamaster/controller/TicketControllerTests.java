package com.javamaster.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.javamaster.controller.abstracts.AbstractJunitControllerTests;
import com.javamaster.converter.CustomUserDetailsToUserConverter;
import com.javamaster.converter.TicketRequestDtoToTicketRawConverter;
import com.javamaster.converter.TicketToTicketResponseDtoConverter;
import com.javamaster.dto.TicketOverview;
import com.javamaster.mother.TicketOverviewMother;
import com.javamaster.service.adapters.TicketOverviewServiceAdapter;
import com.javamaster.service.adapters.TicketServiceAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.mockito.Mockito.when;


@WebMvcTest(TicketController.class)
public class TicketControllerTests extends AbstractJunitControllerTests {
    @MockBean
    private TicketServiceAdapter ticketServiceAdapter;
    @MockBean
    private TicketToTicketResponseDtoConverter converterToDto;
    @MockBean
    private TicketRequestDtoToTicketRawConverter converterFromDto;
    @MockBean
    private CustomUserDetailsToUserConverter userConverter;
    @MockBean
    private TicketOverviewServiceAdapter ticketOverviewService;


    @Test
    @WithUserDetails("employee1_mogilev@yopmail.com")
    public void shouldReturn200AndTicketOverviewWhenRequestByAuthorized() {
        TicketOverview expected = TicketOverviewMother.create().build();
        when(ticketOverviewService.getTicketOverview(1L))
                .thenReturn(expected);
        TicketOverview actual = getData("/tickets/1", new TypeReference<>() {
        });
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturn403WhenRequestedByAnonymous() {
        testForbidden(HttpMethod.GET, "/tickets", null);
    }
}