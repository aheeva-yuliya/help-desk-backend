package com.javamaster.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "categories")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(exclude = "id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String category;
}
