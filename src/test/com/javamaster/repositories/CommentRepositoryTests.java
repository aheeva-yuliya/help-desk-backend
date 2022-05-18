package com.javamaster.repositories;

import com.javamaster.entities.Comment;
import com.javamaster.entities.Ticket;
import com.javamaster.utils.mothers.TicketMother;
import com.javamaster.utils.mothers.UserMother;
import com.javamaster.repositories.abstracts.DatabaseIntegrationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommentRepositoryTests extends DatabaseIntegrationTests {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void shouldFindCommentsWhenSearchedByTicketId() {
        Ticket ticket = TicketMother.create().owner(UserMother.createAuthorized().build()).build();
        saveAndFlush(ticket);

        Comment comment = Comment.builder().ticket(ticket).owner(UserMother.createAuthorized().build()).build();

        Comment expected  = commentRepository.save(comment);

        Assertions.assertEquals(List.of(expected), commentRepository.findCommentsByTicketId(1000L));
    }
}