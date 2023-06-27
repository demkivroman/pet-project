package com.demkiv.pet.project.service.service.imp;

import com.demkiv.pet.project.service.entity.User;
import com.demkiv.pet.project.service.entity.UserDetail;
import com.demkiv.pet.project.service.repository.CustomerRepository;
import com.demkiv.pet.project.service.service.CustomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        Optional<User> user = customerRepository.findByNameAndPassword(username, password);
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
    public Optional<User> findByToken(String token) {
        return customerRepository.findByToken(token);
    }

    @Override
    public UserDetails getUserDetails(String token) {
        User user = Optional.ofNullable(token)
                .map(customerRepository::findByToken)
                .get()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDetail.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .token(user.getToken())
                .build();
    }
}
