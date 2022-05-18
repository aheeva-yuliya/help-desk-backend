package com.javamaster.controllers;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.controllers.abstracts.AbstractJunitControllerTests;
import com.javamaster.converters.CommentRequestDtoToCommentConverter;
import com.javamaster.converters.CommentToCommentResponseDtoConverter;
import com.javamaster.dto.CommentRequestDto;
import com.javamaster.entities.Comment;
import com.javamaster.entities.User;
import com.javamaster.utils.mothers.CustomUserDetailsMother;
import com.javamaster.utils.mothers.TicketMother;
import com.javamaster.utils.mothers.UserMother;
import com.javamaster.services.adapters.CommentServiceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(CommentsController.class)
public class CommentsControllerTests extends AbstractJunitControllerTests {
    @MockBean
    private CommentServiceAdapter commentService;
    @MockBean
    private CommentToCommentResponseDtoConverter toDto;
    @MockBean
    private CommentRequestDtoToCommentConverter fromDto;

    @Test
    @WithUserDetails("employee1_mogilev@yopmail.com")
    public void shouldSaveNew() {
        CommentRequestDto dto = new CommentRequestDto("comment");
        CustomUserDetails details = CustomUserDetailsMother.create().build();
        User user = UserMother.createAuthorized().build();
        Comment comment = Comment.builder()
                .id(null)
                .owner(user)
                .ticket(TicketMother.create().id(2L).build())
                .text("comment")
                .build();

        when(fromDto.convert(dto, details, 2L)).thenReturn(comment);

        sendData(HttpMethod.POST, "/comments/2", dto);

        verify(commentService).addComment(comment);
    }
}