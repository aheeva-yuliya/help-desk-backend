package com.javamaster.services.adapters;

import com.javamaster.dto.TicketRaw;
import com.javamaster.entities.Ticket;
import com.javamaster.entities.User;
import com.javamaster.entities.enums.UserRole;

import java.util.List;

public interface TicketServiceAdapter {
    Long createTicket(TicketRaw rawTicket, String action);

    Long editTicket(TicketRaw ticketRaw, String action);

    Long changeStatus(User user, Long ticketId, String action);

    Ticket saveTicket(Ticket ticket);

    Ticket getById(Long id);

    List<Ticket> getByUserId(Integer userId, UserRole role);

    List<Ticket> getByUserId(Integer userId);
}
