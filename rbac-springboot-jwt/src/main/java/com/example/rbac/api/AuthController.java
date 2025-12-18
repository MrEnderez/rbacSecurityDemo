package com.example.rbac.api;

import com.example.rbac.api.dto.AuthDtos;
import com.example.rbac.security.JwtService;
import com.example.rbac.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<AuthDtos.LoginResponse> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.username(), req.password())
    );

    List<String> roles = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    String token = jwtService.generateToken(req.username(), roles);
    return ResponseEntity.ok(new AuthDtos.LoginResponse(token));
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
    var user = userService.register(req.username(), req.password(), req.roles());
    return ResponseEntity.ok().body(java.util.Map.of(
        "id", user.getId(),
        "username", user.getUsername(),
        "roles", user.getRoles().stream().map(r -> r.getName()).toList()
    ));
  }
}
