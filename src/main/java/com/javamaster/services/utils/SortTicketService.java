package com.javamaster.services.utils;

import com.javamaster.dto.TicketResponseDto;
import com.javamaster.entities.Urgency;
import com.javamaster.repositories.PriorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SortTicketService implements SortTicketsServiceAdapter {
    private final PriorityRepository priorityRepository;

    @Override
    public List<TicketResponseDto> sortByIdAsc(List<TicketResponseDto> tickets) {
        return tickets
                .stream()
                .sorted(Comparator.comparing(TicketResponseDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByIdDesc(List<TicketResponseDto> tickets) {
        return tickets
                .stream()
                .sorted((t1, t2) -> t2.getId().compareTo(t1.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByPriorityDesc(List<TicketResponseDto> tickets) {
        Map<String, Integer> map = loadPriorities();
        return tickets
                .stream()
                .sorted(Comparator.comparing(t -> map.get(t.getUrgency())))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByPriorityAsc(List<TicketResponseDto> tickets) {
        Map<String, Integer> map = loadPriorities();
        return tickets
                .stream()
                .sorted((t1, t2) -> map.get(t2.getUrgency()).compareTo(map.get(t1.getUrgency())))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByNameDesc(List<TicketResponseDto> tickets) {
        return tickets
                .stream()
                .sorted((t1, t2) -> t2.getName().compareTo(t1.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByNameAsc(List<TicketResponseDto> tickets) {
        return tickets
                .stream()
                .sorted(Comparator.comparing(TicketResponseDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByStatusDesc(List<TicketResponseDto> tickets) {
        return tickets
                .stream()
                .sorted((t1, t2) -> t2.getState().compareTo(t1.getState()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByStatusAsc(List<TicketResponseDto> tickets) {
        return tickets
                .stream()
                .sorted(Comparator.comparing(TicketResponseDto::getState))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByDateDesc(List<TicketResponseDto> tickets) {
        return tickets
                .stream()
                .sorted((t1, t2) -> t2.getDesiredResolutionDate().compareTo(t1.getDesiredResolutionDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> sortByDateAsc(List<TicketResponseDto> tickets) {
        return tickets
                .stream()
                .sorted(Comparator.comparing(TicketResponseDto::getDesiredResolutionDate))
                .collect(Collectors.toList());
    }

    private Map<String, Integer> loadPriorities() {
        return priorityRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Urgency::getUrgency, Urgency::getId));
    }
}
