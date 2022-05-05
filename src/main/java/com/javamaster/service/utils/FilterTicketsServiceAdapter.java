package com.javamaster.service.utils;

import com.javamaster.dto.TicketResponseDto;

import java.util.List;

public interface FilterTicketsServiceAdapter {
    List<TicketResponseDto> filterById(List<TicketResponseDto> tickets, String object);

    List<TicketResponseDto> filterByPriority(List<TicketResponseDto> tickets, String object);

    List<TicketResponseDto> filterByName(List<TicketResponseDto> tickets, String object);

    List<TicketResponseDto> filterByStatus(List<TicketResponseDto> tickets, String object);

    List<TicketResponseDto> filterByDate(List<TicketResponseDto> tickets, String object);
}
