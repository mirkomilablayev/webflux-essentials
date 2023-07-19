package com.webfluxessentials.resource;

import com.webfluxessentials.domain.User;
import com.webfluxessentials.dto.auth.request.AuthRequest;
import com.webfluxessentials.dto.auth.request.RegisterRequest;
import com.webfluxessentials.dto.auth.response.AuthResponse;
import com.webfluxessentials.dto.auth.response.RegisterResponse;
import com.webfluxessentials.error.Errors;
import com.webfluxessentials.error.GenericException;
import com.webfluxessentials.repository.UserRepository;
import com.webfluxessentials.configuration.CustomPasswordEncoder;
import com.webfluxessentials.service.jwt.JwtUtils;
import com.webfluxessentials.util.Role;
import com.webfluxessentials.util.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {

    private JwtUtils jwtUtils;
    private CustomPasswordEncoder customPasswordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return userRepository.findByUsername(ar.getUsername())
                .filter(userDetails -> customPasswordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtils.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }


    @PostMapping("/register")
    public Mono<?> register(@RequestBody RegisterRequest registerRequest) {
        return userRepository.existsByUsername(registerRequest.getUsername())
                .flatMap(isExists -> {
                    if (isExists) {
                        return Mono.error(new GenericException(Errors.USERNAME_ALREADY_TAKEN));
                    } else {
                        User user = new User();
                        user.setUsername(registerRequest.getUsername());
                        user.setStatus(UserStatus.ACTIVE);
                        user.setPassword(customPasswordEncoder.encode(registerRequest.getPassword()));
                        user.setFullName(registerRequest.getFullName());
                        user.setRoles(new ArrayList<>(List.of(Role.ROLE_USER)));
                        return userRepository.save(user)
                                .map(savedUser ->new RegisterResponse(
                                        savedUser.getId(),
                                        savedUser.getFullName(),
                                        savedUser.getUsername(),
                                        savedUser.getRoles()
                                ));
                    }
                });
    }

}