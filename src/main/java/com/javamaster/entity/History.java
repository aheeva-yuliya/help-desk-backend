package com.javamaster.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "records")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
    private Timestamp date;
    private String action;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    private String description;
}