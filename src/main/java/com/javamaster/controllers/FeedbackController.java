package com.javamaster.controllers;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.converters.CustomUserDetailsToUserConverter;
import com.javamaster.converters.FeedbackDtoToFeedbackConverter;
import com.javamaster.dto.FeedbackDto;
import com.javamaster.dto.ResponseMessage;
import com.javamaster.entities.Feedback;
import com.javamaster.services.adapters.FeedbackServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final CustomUserDetailsToUserConverter userConverter;
    private final FeedbackDtoToFeedbackConverter feedbackConverter;
    private final FeedbackServiceAdapter feedbackService;

    @PostMapping("/{ticketId}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')")
    public ResponseMessage saveFeedback(@AuthenticationPrincipal final CustomUserDetails user,
                                        @Valid @RequestBody FeedbackDto feedbackDto,
                                        @PathVariable Long ticketId) {
        Feedback feedback = feedbackConverter.convert(feedbackDto);
        feedback.setOwner(userConverter.convert(user));
        feedbackService.saveFeedback(feedback, ticketId);
        return new ResponseMessage("Feedback has been successfully saved.");
    }
}
