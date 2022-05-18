package com.javamaster.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    private int rate;
    private String comment;
    private Timestamp date;
    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
}
