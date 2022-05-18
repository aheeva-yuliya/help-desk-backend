package com.javamaster.converters;

import com.javamaster.dto.FeedbackDto;
import com.javamaster.entities.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackDtoToFeedbackConverter {
    public Feedback convert(FeedbackDto dto) {
        return Feedback.builder()
                .rate(dto.getRate())
                .comment(dto.getComment())
                .build();
    }
}
