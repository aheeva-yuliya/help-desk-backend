package com.javamaster.service;

import com.javamaster.converter.TicketRawToTicketConverter;
import com.javamaster.dto.TicketRaw;
import com.javamaster.entity.Category;
import com.javamaster.entity.Ticket;
import com.javamaster.mother.TicketMother;
import com.javamaster.mother.TicketRawMother;
import com.javamaster.mother.UserMother;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repository.TicketRepository;
import com.javamaster.service.adapters.ActionServiceAdapter;
import com.javamaster.service.adapters.AttachmentServiceAdapter;
import com.javamaster.service.adapters.CommentServiceAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceTests {
    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private DateTimeProvider dateTimeProvider;
    private TicketRawToTicketConverter toTicketConverter;
    private AttachmentServiceAdapter attachmentService;
    private CommentServiceAdapter commentService;
    private ActionServiceAdapter actionService;

    @BeforeEach
    public void init() {
        ticketRepository = Mockito.mock(TicketRepository.class);
        dateTimeProvider = Mockito.mock(DateTimeProvider.class);
        toTicketConverter = Mockito.mock(TicketRawToTicketConverter.class);
        attachmentService = Mockito.mock(AttachmentServiceAdapter.class);
        commentService = Mockito.mock(CommentServiceAdapter.class);
        actionService = Mockito.mock(ActionServiceAdapter.class);

        ticketService = new TicketService(ticketRepository, dateTimeProvider, toTicketConverter, attachmentService,
                commentService, actionService );
    }

    @Nested
    public class CreateTests {

        @Test
        public void shouldSaveTicketWhenSaveAsDraft() {
            Ticket ticket = TicketMother.create().build();
            TicketRaw ticketRaw = TicketRawMother.create().build();
            Mockito.when(dateTimeProvider.now())
                    .thenReturn(Timestamp.valueOf(
                            LocalDateTime.of(2022, 10, 10, 10, 30)
                    ));
            Mockito.when(toTicketConverter.convert(ticketRaw))
                    .thenReturn(ticket);
            Mockito.when(actionService.performAction(UserMother.create().build(),
                    ticket,
                    "Save as Draft"))
                    .thenReturn(Map.of());
            //Mockito.verify(ticketRepository).save(ticket);
        }
    }

    @Test
    void editTicket() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void saveTicket() {
    }

    @Test
    void getById() {
    }

    @Test
    void getByUserId() {
    }
}