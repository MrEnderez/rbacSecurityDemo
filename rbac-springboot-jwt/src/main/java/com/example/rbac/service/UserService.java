package com.example.rbac.service;

import com.example.rbac.domain.AppUser;
import com.example.rbac.domain.Role;
import com.example.rbac.repo.RoleRepository;
import com.example.rbac.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public AppUser register(String username, String rawPassword, Set<String> roleNames) {
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username already exists");
    }

    Set<Role> roles = new HashSet<>();
    if (roleNames == null || roleNames.isEmpty()) {
      roles.add(ensureRole("ROLE_USER"));
    } else {
      for (String rn : roleNames) roles.add(ensureRole(rn));
    }

    AppUser user = AppUser.builder()
        .username(username)
        .passwordHash(passwordEncoder.encode(rawPassword))
        .roles(roles)
        .build();

    return userRepository.save(user);
  }

  public Role ensureRole(String name) {
    return roleRepository.findByName(name)
        .orElseGet(() -> roleRepository.save(Role.builder().name(name).build()));
  }
}
