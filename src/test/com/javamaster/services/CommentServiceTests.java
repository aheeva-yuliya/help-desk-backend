package com.javamaster.services;

import com.javamaster.entities.Comment;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repositories.CommentRepository;
import com.javamaster.utils.mothers.TicketMother;
import com.javamaster.utils.mothers.UserMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTests {
    private CommentRepository commentRepository;
    private DateTimeProvider dateTimeProvider;
    private CommentService commentService;

    @BeforeEach
    public void init() {
        commentRepository = Mockito.mock(CommentRepository.class);
        dateTimeProvider = Mockito.mock(DateTimeProvider.class);

        commentService = new CommentService(commentRepository, dateTimeProvider);
    }

    @Test
    public void shouldAddCommentWhenRequested() {
        Comment comment = Comment.builder()
                .owner(UserMother.createAuthorized().build())
                .ticket(TicketMother.create().id(1L).build())
                .date(Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 0, 0)))
                .text("text")
                .build();

        Mockito.when(dateTimeProvider.now())
                .thenReturn(Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 0, 0)));

        commentService.addComment(comment);

        Mockito.verify(commentRepository).save(comment);
    }
}