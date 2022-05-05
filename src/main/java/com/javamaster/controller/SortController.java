package com.javamaster.controller;

import com.javamaster.dto.TicketResponseDto;
import com.javamaster.service.utils.SortTicketsServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/sort")
public class SortController {
    private final SortTicketsServiceAdapter sortTicketsService;

    @GetMapping()
    public List<TicketResponseDto> sort(@RequestBody List<TicketResponseDto> tickets, @RequestParam String by) {
        switch (by) {
            case ("by id desc"):
                return sortTicketsService.sortByIdDesc(tickets);
            case ("by id asc"):
                return sortTicketsService.sortByIdAsc(tickets);
            case ("by urgency desc"):
                return sortTicketsService.sortByPriorityDesc(tickets);
            case ("by urgency asc"):
                return sortTicketsService.sortByPriorityAsc(tickets);
            case ("by name desc"):
                return sortTicketsService.sortByNameDesc(tickets);
            case ("by name asc"):
                return sortTicketsService.sortByNameAsc(tickets);
            case ("by status asc"):
                return sortTicketsService.sortByStatusAsc(tickets);
            case ("by status desc"):
                return sortTicketsService.sortByStatusDesc(tickets);
            case ("by date asc"):
                return sortTicketsService.sortByDateAsc(tickets);
            case ("by date desc"):
                return sortTicketsService.sortByDateDesc(tickets);
            default:
                return tickets;
        }
    }
}
