package com.javamaster.entities;

import com.javamaster.entities.enums.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@ToString(exclude = "password")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
