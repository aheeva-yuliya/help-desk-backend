package com.javamaster.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.javamaster.converter.CustomUserDetailsToUserConverter;
import com.javamaster.converter.TicketRequestDtoToTicketRawConverter;
import com.javamaster.converter.TicketToTicketResponseDtoConverter;
import com.javamaster.dto.TicketResponseDto;
import com.javamaster.entity.enums.UserRole;
import com.javamaster.mother.TicketMother;
import com.javamaster.service.adapters.TicketOverviewServiceAdapter;
import com.javamaster.service.adapters.TicketServiceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;

import static org.mockito.Mockito.*;

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
    void saveTicket() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void editTicket() {
    }

    @Test
    @WithUserDetails("employee1_mogilev@yopmail.com")
    public void shouldReturn200WhenRequestByAuthorized() {
        when(ticketServiceAdapter.getByUserId(1, UserRole.EMPLOYEE))
                .thenReturn(List.of(
                        TicketMother.create().build()
                ));
        List<TicketResponseDto> expected = List.of(TicketResponseDto.builder().build());
        //List<TicketResponseDto> actual = getData("/tickets", new TypeReference<>() {});
    }

    @Test
    public void shouldReturn401WhenRequestedByAnonymous() {
    }

    @Test
    void getOverviewByTicketId() {
    }
}