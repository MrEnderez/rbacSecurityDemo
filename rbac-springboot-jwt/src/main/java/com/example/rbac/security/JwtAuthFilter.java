package com.example.rbac.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String auth = request.getHeader("Authorization");
    if (auth == null || !auth.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = auth.substring(7).trim();
    try {
      Claims claims = jwtService.parse(token);
      String username = claims.getSubject();

      @SuppressWarnings("unchecked")
      List<String> roles = claims.get("roles", List.class);
      var authorities = roles == null ? List.<SimpleGrantedAuthority>of()
          : roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

      var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);
    } catch (Exception ex) {
      SecurityContextHolder.clearContext();
      filterChain.doFilter(request, response);
    }
  }
}
