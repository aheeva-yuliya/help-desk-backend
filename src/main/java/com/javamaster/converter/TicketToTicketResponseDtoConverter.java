package com.javamaster.converter;

import com.javamaster.dto.TicketResponseDto;
import com.javamaster.entity.Ticket;

import com.javamaster.entity.enums.Action;
import com.javamaster.entity.enums.UserRole;
import com.javamaster.service.ActionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TicketToTicketResponseDtoConverter {
    private final ActionService actionService;

    public TicketResponseDto convert(Ticket source, UserRole userRole) {
        return TicketResponseDto.builder()
                .id(source.getId())
                .name(source.getName())
                .desiredResolutionDate(source.getDesiredResolutionDate())
                .urgency(source.getUrgency().getUrgency())
                .state(source.getState().getValue())
                .action((actionService.setPossibleAction(source.getState(), userRole, source.getOwner().getRole()))
                        .stream()
                        .map(Action::getValue)
                        .collect(Collectors.toList()))
                .build();
    }
}
