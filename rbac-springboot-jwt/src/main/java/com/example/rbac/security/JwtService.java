package com.example.rbac.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

  private final SecretKey key;
  private final String issuer;
  private final long expirationMinutes;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.issuer}") String issuer,
      @Value("${app.jwt.expirationMinutes}") long expirationMinutes
  ) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.issuer = issuer;
    this.expirationMinutes = expirationMinutes;
  }

  public String generateToken(String username, List<String> roles) {
    Instant now = Instant.now();
    Instant exp = now.plus(expirationMinutes, ChronoUnit.MINUTES);
    return Jwts.builder()
        .issuer(issuer)
        .subject(username)
        .issuedAt(Date.from(now))
        .expiration(Date.from(exp))
        .claim("roles", roles)
        .signWith(key)
        .compact();
  }

  public Claims parse(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }
}
