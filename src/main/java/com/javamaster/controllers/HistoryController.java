package com.javamaster.controllers;

import com.javamaster.converters.HistoryToHistoryDtoConverter;
import com.javamaster.dto.HistoryResponseDto;
import com.javamaster.services.adapters.HistoryServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/history")
public class HistoryController {
    private final HistoryServiceAdapter historyService;
    private final HistoryToHistoryDtoConverter converter;

    @GetMapping("/{ticketId}")
    public List<HistoryResponseDto> getAllByTicketId(@PathVariable Long ticketId) {
        return historyService.getHistoryByTicketId(ticketId)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
