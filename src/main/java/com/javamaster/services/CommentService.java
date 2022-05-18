package com.javamaster.services;

import com.javamaster.entities.Comment;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repositories.CommentRepository;
import com.javamaster.services.adapters.CommentServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService implements CommentServiceAdapter {
    private final CommentRepository commentRepository;
    private final DateTimeProvider dateTimeProvider;

    @Override
    public void addComment(Comment comment) {
        comment.setDate(dateTimeProvider.now());
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByTicketId(Long ticketId) {
        return commentRepository.findCommentsByTicketId(ticketId);
    }
}
