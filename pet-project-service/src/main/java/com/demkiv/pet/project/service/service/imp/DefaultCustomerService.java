package com.demkiv.pet.project.service.service.imp;

import com.demkiv.pet.project.service.entity.User;
import com.demkiv.pet.project.service.repository.CustomerRepository;
import com.demkiv.pet.project.service.service.CustomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("customerService")
public class DefaultCustomerService implements CustomService {
    @Autowired
    private final CustomerRepository customerRepository;

    public DefaultCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public String login(String username, String password) {
        Optional<User> user = customerRepository.login(username, password);
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            User customUser = user.get();
            customUser.setToken(token);
            customerRepository.save(customUser);
            return token;
        }

        return StringUtils.EMPTY;
    }

    @Override
    public Optional<org.springframework.security.core.userdetails.User> findByToken(String token) {
        Optional<User> user = customerRepository.findByToken(token);
        if (user.isPresent()) {
            User customUser = user.get();
            org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(
              customUser.getName(), customUser.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(securityUser);
        }

        return Optional.empty();
    }
}
