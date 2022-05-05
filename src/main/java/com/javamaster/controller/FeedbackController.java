package com.javamaster.controller;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.converter.CustomUserDetailsToUserConverter;
import com.javamaster.converter.FeedbackDtoToFeedbackConverter;
import com.javamaster.dto.FeedbackDto;
import com.javamaster.entity.Feedback;
import com.javamaster.service.adapters.FeedbackServiceAdapter;
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

    @PostMapping()
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity<?> saveFeedback(@AuthenticationPrincipal final CustomUserDetails user,
                                          @Valid @RequestBody FeedbackDto feedbackDto,
                                          @RequestParam Long ticketId) {
        Feedback feedback = feedbackConverter.convert(feedbackDto);
        feedback.setOwner(userConverter.convert(user));
        feedbackService.saveFeedback(feedback, ticketId);
        return ResponseEntity.ok("ok");
    }
}
