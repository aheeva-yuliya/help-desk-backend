package com.javamaster.repository;

import com.javamaster.entity.Comment;
import com.javamaster.entity.Ticket;
import com.javamaster.mother.TicketMother;
import com.javamaster.mother.UserMother;
import com.javamaster.repository.abstracts.DatabaseIntegrationTests;
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