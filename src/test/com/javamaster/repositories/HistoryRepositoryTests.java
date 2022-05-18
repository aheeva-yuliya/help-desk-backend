package com.javamaster.repositories;

import com.javamaster.entities.History;
import com.javamaster.entities.Ticket;
import com.javamaster.entities.User;
import com.javamaster.utils.mothers.TicketMother;
import com.javamaster.utils.mothers.UserMother;
import com.javamaster.repositories.abstracts.DatabaseIntegrationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class HistoryRepositoryTests extends DatabaseIntegrationTests {
    @Autowired
    private HistoryRepository historyRepository;

    @Test
    public void shouldFindHistoriesWhenSearchedByTicketId() {
        User user = UserMother.createAuthorized().build();
        Ticket ticket = TicketMother.create().owner(user).build();
        saveAndFlush(ticket);

        History history = historyRepository.save(History.builder()
                .ticket(ticket)
                .owner(user)
                .action("CANCEL")
                .date(Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 0, 0 )))
                .build());

        List<History> expected = List.of(history);
        List<History> actual = historyRepository.findHistoriesByTicketId(1000L);

        Assertions.assertEquals(expected, actual);
    }
}