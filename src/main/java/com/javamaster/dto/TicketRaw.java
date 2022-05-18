package com.javamaster.dto;

import com.javamaster.entities.*;
import com.javamaster.entities.enums.State;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.LocalDate;

@SuperBuilder
@Data
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
