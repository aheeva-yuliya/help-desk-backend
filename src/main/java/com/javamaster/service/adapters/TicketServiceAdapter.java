package com.javamaster.service.adapters;

import com.javamaster.dto.TicketRaw;
import com.javamaster.entity.Ticket;
import com.javamaster.entity.User;
import com.javamaster.entity.enums.UserRole;

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
