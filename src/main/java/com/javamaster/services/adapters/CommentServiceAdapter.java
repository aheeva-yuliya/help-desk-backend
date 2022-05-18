package com.javamaster.services.adapters;

import com.javamaster.entities.Comment;

import java.util.List;

public interface CommentServiceAdapter {
    void addComment(Comment comment);

    List<Comment> getCommentsByTicketId(Long ticketId);
}
