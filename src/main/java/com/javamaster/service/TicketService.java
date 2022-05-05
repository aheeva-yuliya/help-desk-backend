package com.javamaster.service;

import com.javamaster.converter.TicketRawToTicketConverter;
import com.javamaster.dto.TicketRaw;
import com.javamaster.entity.*;
import com.javamaster.entity.enums.State;
import com.javamaster.entity.enums.UserRole;
import com.javamaster.providers.DateTimeProvider;
import com.javamaster.repository.TicketRepository;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import com.javamaster.service.adapters.ActionServiceAdapter;
import com.javamaster.service.adapters.AttachmentServiceAdapter;
import com.javamaster.service.adapters.CommentServiceAdapter;
import com.javamaster.service.adapters.TicketServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService implements TicketServiceAdapter {
    private final TicketRepository ticketRepository;
    private final DateTimeProvider dateTimeProvider;
    private final TicketRawToTicketConverter toTicketConverter;
    private final AttachmentServiceAdapter attachmentService;
    private final CommentServiceAdapter commentService;
    private final ActionServiceAdapter actionService;

    @Override
    @Transactional
    public void createTicket(TicketRaw rawTicket, String action) {
        Timestamp now = dateTimeProvider.now();
        rawTicket.setCreatedOn(now);

        if (action.equals("Submit")) {
            checkFields(rawTicket);
        }

        Ticket ticket = toTicketConverter.convert(rawTicket);

        completeTicketAction(rawTicket, ticket.getOwner(), action, ticket);
    }

    @Override
    @Transactional
    public void editTicket(TicketRaw ticketRaw, String action) {
        Ticket existed = getById(ticketRaw.getId());

        checkOwner(existed, ticketRaw.getOwner());
        existed.setCategory(ticketRaw.getCategory());
        existed.setName(ticketRaw.getName());
        existed.setDescription(ticketRaw.getDescription());
        existed.setUrgency(ticketRaw.getUrgency());

        if (action.equals("Submit")) {
            checkFields(ticketRaw);
        }

        completeTicketAction(ticketRaw, existed.getOwner(), action, existed);
    }

    @Override
    @Transactional
    public void changeStatus(User user, Long ticketId, String action) {
        Ticket ticket = getById(ticketId);

        completeTicketAction(null, user, action, ticket);
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Ticket %d not found.", id)));
    }

    @Override
    public List<Ticket> getByUserId(Integer userId, UserRole role) {
        List<Ticket> tickets;
        if (role.equals(UserRole.MANAGER)) {
            tickets = ticketRepository.findAllByManagerIdOrderByUrgencyIdDesiredResolutionDate(userId, State.DRAFT);
            return checkManagerList(tickets, userId);
        } else if (role.equals(UserRole.ENGINEER)) {
            tickets = ticketRepository.findAllByEngineerIdOrderByUrgencyIdDesiredResolutionDate(
                    State.APPROVED, State.PROGRESS, State.DONE);
            return checkEngineerList(tickets, userId);
        } else {
            return ticketRepository
                    .findAllByEmployeeIdOrderByUrgencyIdDesiredResolutionDate(userId);
        }
    }

    private void completeTicketAction(TicketRaw rawTicket, User user, String action, Ticket ticket) {
        Map<String, Object> props = actionService.performAction(user, ticket, action);

        Ticket saved = saveTicket(ticket);

        actionService.completeAction(saved, props);

        if (rawTicket != null) {
            checkAttachment(rawTicket.getAttachment(), ticket);

            checkComment(rawTicket.getComment(), ticket);
        }
    }

    private void checkOwner(Ticket ticket, User owner) {
        if (!ticket.getOwner().equals(owner)) {
            throw new ForbiddenException("Ticket could only be edited by the owner");
        }
    }

    private void checkFields(TicketRaw ticketRaw) {
        if (ticketRaw.getCategory() == null) {
            throw new BadRequestException("Category filed should be filled.");
        }
        if (ticketRaw.getName() == null) {
            throw new BadRequestException("Name filed should be filled.");
        }
        if (ticketRaw.getUrgency() == null) {
            throw new BadRequestException("Urgency filed should be filled.");
        }
    }

    private void checkAttachment(Attachment attachment, Ticket ticket) {
        if (attachment != null) {
            attachment.setTicket(ticket);
            attachmentService.addAttachment(attachment);
        }
    }

    private void checkComment(Comment comment, Ticket ticket) {
        if (comment != null) {
            comment.setTicket(ticket);
            commentService.addComment(comment);
        }
    }

    private List<Ticket> checkManagerList(List<Ticket> tickets, Integer userId) {
        return tickets
                .stream()
                .filter(ticket -> ticket.getState().equals(State.NEW)
                        || checkNull(ticket.getApprover(), userId)
                        || checkNull(ticket.getOwner(), userId)
                )
                .collect(Collectors.toList());
    }

    private List<Ticket> checkEngineerList(List<Ticket> tickets, Integer userId) {
        return tickets
                .stream()
                .filter(ticket -> ticket.getState().equals(State.APPROVED)
                        || checkNull(ticket.getAssignee(), userId))
                .collect(Collectors.toList());
    }

    private boolean checkNull(User user, Integer userId) {
        return user != null && userId.equals(user.getId());
    }
}
