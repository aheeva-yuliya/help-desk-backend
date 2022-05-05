package com.javamaster.controller;

import com.javamaster.service.adapters.TicketServiceAdapter;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TicketController.class)
public class TicketControllerTests extends AbstractControllerTests {
    @MockBean
    private TicketServiceAdapter ticketService;
//
//    @Test
//    @WithUserDetails("employee1_mogilev@yopmail.com")
//    public void shouldReturn200WhenTicketSaved() {
////        sendData(HttpMethod.POST, "/tickets", TicketRequestDto.builder()
////                .name("new ticket")
////                .category("Application & Services")
////                .urgency("Critical")
////                .desiredResolutionDate(LocalDate.of(2022, 10, 10))
////                .build()
////        );
//    }
//
//    @Test
//    void changeStatus() {
//    }
//
//    @Test
//    void editTicket() {
//    }
//
//    @Test
//    void getAllTickets() {
//    }
//
//    @Test
//    void getOverviewByTicketId() {
//    }
}