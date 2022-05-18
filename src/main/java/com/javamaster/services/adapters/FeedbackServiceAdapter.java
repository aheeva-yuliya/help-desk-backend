package com.javamaster.services.adapters;

import com.javamaster.entities.Feedback;

public interface FeedbackServiceAdapter {
    void saveFeedback(Feedback feedback, Long ticketId);
}
