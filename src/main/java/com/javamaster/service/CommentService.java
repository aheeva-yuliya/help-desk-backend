package com.javamaster.service;

import com.javamaster.entity.Comment;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repository.CommentRepository;
import com.javamaster.service.adapters.CommentServiceAdapter;
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
