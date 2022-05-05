package com.javamaster.controller;

import com.javamaster.converter.HistoryToHistoryDtoConverter;
import com.javamaster.dto.HistoryResponseDto;
import com.javamaster.service.adapters.HistoryServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/history")
public class HistoryController {
    private final HistoryServiceAdapter historyService;
    private final HistoryToHistoryDtoConverter converter;

    @GetMapping()
    public List<HistoryResponseDto> getAllByTicketId(@RequestParam Long ticketId) {
        return historyService.getHistoryByTicketId(ticketId)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
