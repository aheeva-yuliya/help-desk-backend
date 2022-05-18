package com.javamaster.controllers;

import com.javamaster.dto.TicketResponseDto;
import com.javamaster.services.utils.FilterTicketsServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/filter")
public class FilterController {
    private final FilterTicketsServiceAdapter filterTicketsService;

    @GetMapping()
    public List<TicketResponseDto> filter(@RequestBody List<TicketResponseDto> tickets,
                                          @RequestParam String by,
                                          @RequestParam String criteria) {
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
}
