package com.demkiv.pet.project.service.entity.security;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final String rolePrefix = "ROLE_";
        final String privilegePrefix = "PRIVILEGE_";
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities = this.roles.stream()
//                .map(Role::getName)
//                .map(role -> rolePrefix + role)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());

        Role r = this.roles.stream().findFirst().get();
        log.debug("r ==> " + r);

//        authorities.addAll(this.roles.stream().findFirst().get().getPrivileges().stream()
//                .map(Role::getPrivileges)
//                .flatMap(Collection::stream)
//                .map(privilege -> privilegePrefix + privilege)
//                .map(SimpleGrantedAuthority::new)
//                .toList());

        log.debug("GrantedAuthorities my " );

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
