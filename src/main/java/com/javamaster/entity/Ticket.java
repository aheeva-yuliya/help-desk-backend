package com.javamaster.entity;

import com.javamaster.entity.enums.State;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

@Table(name = "tickets")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Timestamp createdOn;
    private LocalDate desiredResolutionDate;
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "urgency_id")
    private Urgency urgency;
    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;
}


