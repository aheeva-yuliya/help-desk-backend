package com.javamaster.controller;

import com.javamaster.dto.TicketResponseDto;
import com.javamaster.service.utils.FilterTicketsServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/filter")
public class FilterController {
    private final FilterTicketsServiceAdapter filterTicketsService;
    private List<TicketResponseDto> saved;

    @GetMapping()
    public List<TicketResponseDto> filter(@RequestBody List<TicketResponseDto> tickets,
                                          @RequestParam String by,
                                          @RequestParam String criteria) {
        saved = tickets;

        switch (by) {
            case ("id"):
                return filterTicketsService.filterById(tickets, criteria);
            case ("urgency"):
                return filterTicketsService.filterByPriority(tickets, criteria);
            case ("name"):
                return filterTicketsService.filterByName(tickets, criteria);
            case ("state"):
                return filterTicketsService.filterByStatus(tickets, criteria);
            case ("date"):
                return filterTicketsService.filterByDate(tickets, criteria);
            default:
                return tickets;
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @GetMapping("/return")
    public List<TicketResponseDto> returnSaved() {
        return saved;
    }
}