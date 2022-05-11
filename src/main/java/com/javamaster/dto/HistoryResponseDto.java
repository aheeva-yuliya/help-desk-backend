package com.javamaster.dto;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@Data
public class HistoryResponseDto {
    private Timestamp date;
    private String user;
    private String action;
    private String description;
}
