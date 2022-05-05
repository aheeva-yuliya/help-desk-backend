package com.javamaster.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
public class HistoryResponseDto {
    private Timestamp date;
    private String user;
    private String action;
    private String description;
}
