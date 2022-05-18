package com.javamaster.repository;

import com.javamaster.entity.Ticket;
import com.javamaster.entity.Urgency;
import com.javamaster.entity.User;
import com.javamaster.mother.UserMother;
import com.javamaster.repository.abstracts.DatabaseIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketRepositoryTests extends DatabaseIntegrationTests {
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void shouldFindAllByEmployeeIdOrderByUrgencyIdDesiredResolutionDate() {
        User user = UserMother.create().id(1000).build();
        User userIvan =UserMother.createIvan().id(1001).build();

        saveAndFlush(
                UserMother.create().id(null).build(),
                UserMother.createIvan().id(null).build(),
                Ticket.builder()
                        .owner(user)
                        .urgency(Urgency.builder().id(1).urgency("Critical").build())
                        .desiredResolutionDate(LocalDate.of(2022, 10, 10))
                       .build(),
                Ticket.builder()
                        .owner(user)
                        .urgency(Urgency.builder().id(1).urgency("Critical").build())
                        .desiredResolutionDate(LocalDate.of(2022, 10, 9))
                        .build(),
                Ticket.builder()
                        .owner(user)
                        .urgency(Urgency.builder().id(4).urgency("Low").build())
                        .desiredResolutionDate(LocalDate.of(2022, 10, 10))
                        .build(),
                Ticket.builder()
                        .owner(userIvan)
                        .urgency(Urgency.builder().id(1).urgency("Critical").build())
                        .desiredResolutionDate(LocalDate.of(2022, 10, 10))
                        .build()
        );

         List<Ticket> expected = List.of (
                Ticket.builder()
                        .id(1001L)
                        .owner(user)
                        .urgency(Urgency.builder().id(1).urgency("Critical").build())
                        .desiredResolutionDate(LocalDate.of(2022, 10, 9))
                        .build(),
                Ticket.builder()
                        .id(1000L)
                        .owner(user)
                        .urgency(Urgency.builder().id(1).urgency("Critical").build())
                        .desiredResolutionDate(LocalDate.of(2022, 10, 10))
                        .build(),
                Ticket.builder()
                        .id(1002L)
                        .owner(user)
                        .urgency(Urgency.builder().id(4).urgency("Low").build())
                        .desiredResolutionDate(LocalDate.of(2022, 10, 10))
                        .build()
        );
        List<Ticket> actual = ticketRepository.findAllByEmployeeIdOrderByUrgencyIdDesiredResolutionDate(1000);

        assertEquals(expected, actual);
    }
}