package com.javamaster.controllers;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.converters.CommentRequestDtoToCommentConverter;
import com.javamaster.converters.CommentToCommentResponseDtoConverter;
import com.javamaster.dto.CommentRequestDto;
import com.javamaster.dto.CommentResponseDto;
import com.javamaster.services.adapters.CommentServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentsController {
    private final CommentServiceAdapter commentService;
    private final CommentToCommentResponseDtoConverter toDto;
    private final CommentRequestDtoToCommentConverter fromDto;

    @GetMapping("/{ticketId}")
    public List<CommentResponseDto> getAllByTicketId(@PathVariable Long ticketId) {
        return commentService.getCommentsByTicketId(ticketId)
                .stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
    }

    @PostMapping("/{ticketId}")
    public ResponseEntity<String> saveNew(@AuthenticationPrincipal final CustomUserDetails user,
                                          @Valid @RequestBody CommentRequestDto dto,
                                          @PathVariable Long ticketId) {
        commentService.addComment(fromDto.convert(dto, user, ticketId));
        return ResponseEntity.ok("Comment has been successfully added.");
    }
}
