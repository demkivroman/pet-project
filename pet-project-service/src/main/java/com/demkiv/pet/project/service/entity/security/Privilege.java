package com.demkiv.pet.project.service.entity.security;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
