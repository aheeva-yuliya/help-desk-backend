package com.javamaster.services;

import com.javamaster.entities.Attachment;
import com.javamaster.entities.Ticket;
import com.javamaster.repositories.AttachmentRepository;
import com.javamaster.services.adapters.HistoryServiceAdapter;
import com.javamaster.utils.mothers.HistoryMother;
import com.javamaster.utils.mothers.TicketMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AttachmentServiceTests {
    private AttachmentRepository attachmentRepository;
    private HistoryServiceAdapter historyService;
    private AttachmentService attachmentService;

    @BeforeEach
    public void init() {
        attachmentRepository = Mockito.mock(AttachmentRepository.class);
        historyService = Mockito.mock(HistoryServiceAdapter.class);

        attachmentService = new AttachmentService(attachmentRepository, historyService);
    }

    @Test
    public void shouldAddAttachmentWhenRequested() {
        Ticket ticket = TicketMother.create().id(1L).build();

        Attachment attachment = Attachment.builder()
                .ticket(ticket)
                .name("name")
                .build();

        attachmentService.addAttachment(attachment);

        Mockito.verify(attachmentRepository).save(attachment);
        Mockito.verify(historyService).saveHistory(
                HistoryMother.create()
                        .description("File is attached: [name].")
                        .owner(attachment.getTicket().getOwner())
                        .ticket(ticket)
                        .build()
        );
    }
}