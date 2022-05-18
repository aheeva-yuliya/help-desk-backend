package com.javamaster.repositories;

import com.javamaster.entities.Attachment;
import com.javamaster.entities.Ticket;
import com.javamaster.utils.mothers.TicketMother;
import com.javamaster.utils.mothers.UserMother;
import com.javamaster.repositories.abstracts.DatabaseIntegrationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AttachmentRepositoryTests extends DatabaseIntegrationTests {
    @Autowired
    private AttachmentRepository attachmentRepository;


    @Test
    public void shouldFindAttachmentsByTicketIdWhenSaved() {
        Ticket ticket = TicketMother.create().owner(UserMother.createAuthorized().build()).build();
        saveAndFlush(ticket);

        Attachment attachment = Attachment.builder()
                .name("attachment")
                .ticket(ticket)
                .build();


        attachment.setId(attachmentRepository.saveAndFlush(attachment).getId());

        Assertions.assertEquals(List.of(attachment), attachmentRepository.findAttachmentsByTicketId(1000L));
    }
}