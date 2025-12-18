package com.example.rbac.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoApiController {

  @GetMapping("/public/ping")
  public Map<String, Object> ping() {
    return Map.of("ok", true, "message", "public endpoint");
  }

  @GetMapping("/user/me")
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public Map<String, Object> me(Authentication authentication) {
    return Map.of(
        "username", authentication.getName(),
        "authorities", authentication.getAuthorities()
    );
  }

  @GetMapping("/admin/panel")
  @PreAuthorize("hasRole('ADMIN')")
  public Map<String, Object> admin(Authentication authentication) {
    return Map.of(
        "ok", true,
        "message", "admin panel",
        "by", authentication.getName()
    );
  }
}
