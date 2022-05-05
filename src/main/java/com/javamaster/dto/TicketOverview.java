package com.javamaster.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketOverview {
    private Long id;
    private String name;
    private String description;
    private Timestamp createdOn;
    private LocalDate desiredResolutionDate;
    private String category;
    private String status;
    private String urgency;

    private String owner;
    private String approver;
    private String assignee;

    private List<AttachmentResponseDto> attachments;
    private List<HistoryResponseDto> history;
    private List<CommentResponseDto> comments;
}
