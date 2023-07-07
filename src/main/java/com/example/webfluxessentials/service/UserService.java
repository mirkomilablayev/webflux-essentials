package com.example.webfluxessentials.service;

import com.example.webfluxessentials.domain.User;
import com.example.webfluxessentials.repository.UserRepository;
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