package com.demkiv.pet.project.service.entity.security;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="authority")
public class UserAuthorities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
