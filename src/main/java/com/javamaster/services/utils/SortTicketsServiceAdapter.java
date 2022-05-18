package com.javamaster.services.utils;

import com.javamaster.dto.TicketResponseDto;

import java.util.List;

public interface SortTicketsServiceAdapter {
    List<TicketResponseDto> sortByIdAsc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByIdDesc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByPriorityDesc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByPriorityAsc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByNameDesc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByNameAsc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByStatusDesc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByStatusAsc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByDateDesc(List<TicketResponseDto> tickets);

    List<TicketResponseDto> sortByDateAsc(List<TicketResponseDto> tickets);
}
