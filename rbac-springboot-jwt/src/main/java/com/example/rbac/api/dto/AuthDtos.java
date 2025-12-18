package com.example.rbac.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class AuthDtos {
  public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
  public record LoginResponse(String token) {}
  public record RegisterRequest(
      @NotBlank @Size(min=3,max=64) String username,
      @NotBlank @Size(min=6,max=128) String password,
      Set<String> roles
  ) {}
}
