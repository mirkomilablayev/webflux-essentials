package com.webfluxessentials.service;

import com.webfluxessentials.domain.User;
import com.webfluxessentials.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.empty());
    }
}