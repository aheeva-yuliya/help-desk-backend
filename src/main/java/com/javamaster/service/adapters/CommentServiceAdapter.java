package com.javamaster.service.adapters;

import com.javamaster.entity.Comment;

import java.util.List;

public interface CommentServiceAdapter {
    void addComment(Comment comment);

    List<Comment> getCommentsByTicketId(Long ticketId);
}
