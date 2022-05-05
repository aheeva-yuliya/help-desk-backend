package com.javamaster.controller;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.converter.CommentRequestDtoToCommentConverter;
import com.javamaster.converter.CommentToCommentResponseDtoConverter;
import com.javamaster.dto.CommentRequestDto;
import com.javamaster.dto.CommentResponseDto;
import com.javamaster.entity.Comment;
import com.javamaster.service.adapters.CommentServiceAdapter;
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

    @GetMapping()
    public List<CommentResponseDto> getAllByTicketId(@RequestParam Long ticketId) {
        return commentService.getCommentsByTicketId(ticketId)
                .stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<?> saveNew(@AuthenticationPrincipal final CustomUserDetails user,
                                     @Valid @RequestBody CommentRequestDto dto,
                                     @RequestParam Long ticketId) {
        Comment comment = fromDto.convert(dto, user, ticketId);
        commentService.addComment(comment);
        return ResponseEntity.ok("comment added");
    }
}
