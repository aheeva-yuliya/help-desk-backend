package com.javamaster.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    private String text;
    private Timestamp date;
    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
}
