package com.javamaster.service;

import com.javamaster.entity.Feedback;
import com.javamaster.entity.Ticket;
import com.javamaster.entity.enums.State;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repository.FeedbackRepository;
import com.javamaster.service.adapters.ActionServiceAdapter;
import com.javamaster.service.adapters.FeedbackServiceAdapter;
import com.javamaster.service.adapters.TicketServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.ForbiddenException;
import java.util.Map;

@Service
@AllArgsConstructor
public class FeedbackService implements FeedbackServiceAdapter {
    private final FeedbackRepository feedbackRepository;
    private final DateTimeProvider dateTimeProvider;
    private final TicketServiceAdapter ticketService;
    private final ActionServiceAdapter actionService;

    @Override
    @Transactional
    public void saveFeedback(Feedback feedback, Long ticketId) {
        Ticket ticket = ticketService.getById(ticketId);

        if (!ticket.getOwner().equals(feedback.getOwner())) {
            throw new ForbiddenException("Forbidden to leave feedback for not owner user");
        }

        if (!ticket.getState().equals(State.DONE)) {
            throw new ForbiddenException("Forbidden to leave feedback with state " + ticket.getState());
        }

        feedback.setDate(dateTimeProvider.now());
        feedback.setTicket(ticket);
        feedbackRepository.save(feedback);

        Map<String, Object> props = actionService.performAction(feedback.getOwner(), ticket, "Leave feedback");
        actionService.completeAction(ticket, props);
    }
}
