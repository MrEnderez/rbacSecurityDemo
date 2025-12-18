package com.example.rbac.bootstrap;

import com.example.rbac.repo.UserRepository;
import com.example.rbac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

  private final UserService userService;
  private final UserRepository userRepository;

  @Override
  public void run(String... args) {
    if (!userRepository.existsByUsername("admin")) {
      userService.register("admin", "Admin123!", Set.of("ROLE_ADMIN"));
    }
    if (!userRepository.existsByUsername("user")) {
      userService.register("user", "User123!", Set.of("ROLE_USER"));
    }
  }
}
