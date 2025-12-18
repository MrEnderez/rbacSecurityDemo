package com.example.rbac.security;

import com.example.rbac.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    var authorities = user.getRoles().stream()
        .map(r -> new SimpleGrantedAuthority(r.getName()))
        .collect(Collectors.toSet());

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPasswordHash())
        .authorities(authorities)
        .build();
  }
}
