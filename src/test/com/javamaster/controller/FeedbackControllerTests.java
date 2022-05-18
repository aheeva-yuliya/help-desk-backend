package com.javamaster.controller;

import com.javamaster.controller.abstracts.AbstractJunitControllerTests;
import com.javamaster.converter.CustomUserDetailsToUserConverter;
import com.javamaster.converter.FeedbackDtoToFeedbackConverter;
import com.javamaster.dto.FeedbackDto;
import com.javamaster.entity.Feedback;
import com.javamaster.mother.CustomUserDetailsMother;
import com.javamaster.mother.UserMother;
import com.javamaster.service.adapters.FeedbackServiceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTests extends AbstractJunitControllerTests {
    @MockBean
    private CustomUserDetailsToUserConverter userConverter;
    @MockBean
    private FeedbackDtoToFeedbackConverter feedbackConverter;
    @MockBean
    private FeedbackServiceAdapter feedbackService;

    @Test
    @WithUserDetails("employee1_mogilev@yopmail.com")
    public void shouldSaveFeedbackWhenRequestByAuthorizedOwner() {
        FeedbackDto dto = new FeedbackDto(4, "feedback comment");
        Feedback feedback = Feedback.builder().rate(4).comment("feedback comment").build();
        when(feedbackConverter.convert(dto)).thenReturn(feedback);
        when(userConverter.convert(CustomUserDetailsMother.create().build()))
                .thenReturn(UserMother.createAuthorized().build());

        sendData(HttpMethod.POST, "/feedbacks/1", dto);

        verify(feedbackService).saveFeedback(feedback, 1L);
    }
}