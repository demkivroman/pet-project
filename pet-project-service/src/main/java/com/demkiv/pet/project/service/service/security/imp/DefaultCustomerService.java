package com.demkiv.pet.project.service.service.security.imp;

import com.demkiv.pet.project.service.entity.security.Privilege;
import com.demkiv.pet.project.service.entity.security.Role;
import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.entity.security.UserDetail;
import com.demkiv.pet.project.service.repository.security.UserRepository;
import com.demkiv.pet.project.service.service.security.CustomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("customerService")
public class DefaultCustomerService implements CustomService {
    @Autowired
    private final UserRepository userRepository;

    public DefaultCustomerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String login(String username, String password) {
        Optional<User> user = userRepository.findByNameAndPassword(username, password);
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            User customUser = user.get();
            customUser.setToken(token);
            userRepository.save(customUser);
            return token;
        }

        return StringUtils.EMPTY;
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public UserDetails getUserDetails(String token) {
        User user = Optional.ofNullable(token)
                .map(userRepository::findByToken)
                .get()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDetail.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .token(user.getToken())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        User user = Optional.ofNullable(token)
                .map(userRepository::findByToken)
                .get()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return getGrantedAuthorities(getPrivileges(user.getRoles()));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
