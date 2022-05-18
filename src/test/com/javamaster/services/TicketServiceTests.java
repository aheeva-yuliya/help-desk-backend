package com.javamaster.services;

import com.javamaster.converters.TicketRawToTicketConverter;
import com.javamaster.dto.TicketRaw;
import com.javamaster.entities.Ticket;
import com.javamaster.utils.mothers.TicketMother;
import com.javamaster.utils.mothers.TicketRawMother;
import com.javamaster.utils.mothers.UserMother;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repositories.TicketRepository;
import com.javamaster.services.adapters.ActionServiceAdapter;
import com.javamaster.services.adapters.AttachmentServiceAdapter;
import com.javamaster.services.adapters.CommentServiceAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.ForbiddenException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


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
                commentService, actionService);
    }


    @Test
    public void shouldSaveTicketWhenSaveAsDraft() {
        Ticket ticket = TicketMother.create()
                .createdOn(Timestamp.valueOf(LocalDateTime.of(2022, 10, 10, 10, 30)))
                .build();
        TicketRaw ticketRaw = TicketRawMother.create().build();
        Ticket saved = TicketMother.create().id(1L).build();
        Map<String, Object> pros = new HashMap<>();
        Mockito.when(dateTimeProvider.now())
                .thenReturn(Timestamp.valueOf(
                        LocalDateTime.of(2022, 10, 10, 10, 30)
                ));
        Mockito.when(toTicketConverter.convert(ticketRaw))
                .thenReturn(ticket);
        Mockito.when(actionService.performAction(UserMother.create().build(),
                        ticket,
                        "Save as Draft"))
                .thenReturn(pros);
        Mockito.when(ticketRepository.save(ticket)).thenReturn(saved);
        ticketService.createTicket(ticketRaw, "Save as Draft");
        Mockito.verify(ticketRepository).save(ticket);
        Mockito.verify(actionService).completeAction(saved, pros);
        Mockito.verify(attachmentService, Mockito.never()).addAttachment(Mockito.any());
        Mockito.verify(commentService, Mockito.never()).addComment(Mockito.any());
    }

    @Test
    public void shouldThrowExceptionWhenNotOwnerEditTicket() {
        TicketRaw ticketRaw = TicketRawMother.create().id(1L).owner(UserMother.create().id(2).build()).build();
        Ticket ticket = TicketMother.create().build();
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        Exception exception = Assertions.assertThrows(ForbiddenException.class, () ->
                ticketService.editTicket(ticketRaw, "Save as Draft")
        );
        Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any());
        String expectedMessage = "Ticket could only be edited by the owner";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}