package com.javamaster.converter;

import com.javamaster.dto.TicketRaw;
import com.javamaster.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketRawToTicketConverter {
    public Ticket convert(TicketRaw source) {
        return Ticket.builder()
                .owner(source.getOwner())
                .category(source.getCategory())
                .name(source.getName())
                .description(source.getDescription())
                .urgency(source.getUrgency())
                .createdOn(source.getCreatedOn())
                .state(source.getState())
                .desiredResolutionDate(source.getDesiredResolutionDate())
                .build();
    }
}
