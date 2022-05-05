package com.javamaster.service.adapters;

import com.javamaster.entity.Feedback;

public interface FeedbackServiceAdapter {
    void saveFeedback(Feedback feedback, Long ticketId);
}
