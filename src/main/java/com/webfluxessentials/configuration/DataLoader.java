package com.webfluxessentials.configuration;



import com.webfluxessentials.domain.User;
import com.webfluxessentials.repository.UserRepository;
import com.webfluxessentials.util.Role;
import com.webfluxessentials.util.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

private static final String ADMIN_USERNAME = "administrator";


    @Override
    public void run(String... args) {
      userRepository.existsByUsername(ADMIN_USERNAME)
              .flatMap(exist -> {
                  if (!exist){
                      User user = new User();
                      user.setFullName("administrator");
                      user.setUsername(ADMIN_USERNAME);
                      user.setPassword(passwordEncoder.encode("admin_00999"));
                      user.setStatus(UserStatus.ACTIVE);
                      user.setRoles(new ArrayList<>(List.of(Role.ROLE_ADMIN)));
                      return userRepository.save(user);
                  }
                  return Mono.empty();
              })
              .subscribe();
    }
}
