package com.demkiv.pet.project.service.entity.security;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "u_Authority")
@Table(name = "u_Authority")
public class UserAuthorities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
