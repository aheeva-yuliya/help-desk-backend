package com.javamaster.service.utils;

import com.javamaster.dto.TicketResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilterTicketsService implements FilterTicketsServiceAdapter {
    @Override
    public List<TicketResponseDto> filterById(List<TicketResponseDto> tickets, String object) {
        try {
            Long filter = Long.parseLong(object);
            return tickets
                    .stream()
                    .filter(t -> t.getId().equals(filter))
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<TicketResponseDto> filterByPriority(List<TicketResponseDto> tickets, String object) {
        return tickets
                .stream()
                .filter(t -> t.getUrgency().equals(object))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> filterByName(List<TicketResponseDto> tickets, String object) {
        return tickets
                .stream()
                .filter(t -> t.getName().equals(object))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> filterByStatus(List<TicketResponseDto> tickets, String object) {
        return tickets
                .stream()
                .filter(t -> t.getState().equals(object))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> filterByDate(List<TicketResponseDto> tickets, String object) {
        try {
            LocalDate filter = LocalDate.parse(object);
            return tickets
                    .stream()
                    .filter(t -> t.getDesiredResolutionDate().equals(filter))
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

