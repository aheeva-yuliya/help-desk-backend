package com.javamaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TicketResponseDto {
    private Long id;
    private String name;
    private LocalDate desiredResolutionDate;
    private String urgency;
    private String state;
    private List<String> action;
}
