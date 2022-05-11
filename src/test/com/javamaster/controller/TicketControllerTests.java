package com.javamaster.controller;

import com.javamaster.converter.CustomUserDetailsToUserConverter;
import com.javamaster.converter.TicketRequestDtoToTicketRawConverter;
import com.javamaster.converter.TicketToTicketResponseDtoConverter;
import com.javamaster.service.adapters.TicketOverviewServiceAdapter;
import com.javamaster.service.adapters.TicketServiceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithUserDetails;


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
    public void shouldReturn200WhenRequestByAuthorized() {
    }

    @Test
    public void shouldReturn403WhenRequestedByAnonymous() {
        testForbidden(HttpMethod.GET, "/tickets", null);
    }
}