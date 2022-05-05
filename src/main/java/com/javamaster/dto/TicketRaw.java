package com.javamaster.dto;

import com.javamaster.entity.*;
import com.javamaster.entity.enums.State;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class TicketRaw {
    private Long id;
    private User owner;
    private String name;
    private String description;
    private LocalDate desiredResolutionDate;
    private State state;
    private Category category;
    private Urgency urgency;
    private Comment comment;
    private Attachment attachment;
    private Timestamp createdOn;
}
