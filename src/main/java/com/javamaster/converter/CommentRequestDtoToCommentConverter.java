package com.javamaster.converter;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.dto.CommentRequestDto;
import com.javamaster.entity.Comment;
import com.javamaster.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentRequestDtoToCommentConverter {
    private final CustomUserDetailsToUserConverter userConverter;
    private final TicketService ticketService;

    public Comment convert(CommentRequestDto source, CustomUserDetails userDetails, Long ticketId) {
        if (source == null) {
            return null;
        } else {
            return Comment.builder()
                    .owner(userConverter.convert(userDetails))
                    .text(source.getComment())
                    .ticket(ticketId == null ? null : ticketService.getById(ticketId))
                    .build();
        }
    }
}
