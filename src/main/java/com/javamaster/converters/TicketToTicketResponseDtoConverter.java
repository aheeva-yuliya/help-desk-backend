package com.javamaster.converters;

import com.javamaster.dto.TicketResponseDto;
import com.javamaster.entities.Ticket;

import com.javamaster.entities.User;
import com.javamaster.entities.enums.Action;
import com.javamaster.services.ActionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TicketToTicketResponseDtoConverter {
    private final ActionService actionService;

    public TicketResponseDto convert(Ticket source, User user) {
        return TicketResponseDto.builder()
                .id(source.getId())
                .name(source.getName())
                .desiredResolutionDate(source.getDesiredResolutionDate())
                .urgency(source.getUrgency().getUrgency())
                .state(source.getState().getValue())
                .action((actionService.setPossibleAction(source.getState(), user, source.getOwner()))
                        .stream()
                        .map(Action::getValue)
                        .collect(Collectors.toList()))
                .build();
    }
}
