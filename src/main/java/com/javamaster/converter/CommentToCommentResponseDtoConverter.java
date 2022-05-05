package com.javamaster.converter;

import com.javamaster.dto.CommentResponseDto;
import com.javamaster.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentToCommentResponseDtoConverter {

    public CommentResponseDto convert(Comment comment) {
        return CommentResponseDto.builder()
                .date(comment.getDate())
                .user(comment.getOwner().getName())
                .text(comment.getText())
                .build();
    }
}
