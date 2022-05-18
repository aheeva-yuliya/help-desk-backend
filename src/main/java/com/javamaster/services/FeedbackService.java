package com.javamaster.services;

import com.javamaster.entities.Feedback;
import com.javamaster.entities.Ticket;
import com.javamaster.entities.enums.State;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repositories.FeedbackRepository;
import com.javamaster.services.adapters.ActionServiceAdapter;
import com.javamaster.services.adapters.FeedbackServiceAdapter;
import com.javamaster.services.adapters.TicketServiceAdapter;
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
