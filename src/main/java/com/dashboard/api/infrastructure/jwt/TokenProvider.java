package com.dashboard.api.infrastructure.jwt;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenProvider {

  @Value("${security.jwt.secret-key}")
  private String secret;
  @Value("${security.jwt.issuer}")
  private String issuer;
  @Value("${security.jwt.expiry-time-in-seconds}")
  private Long expirationTimeInSeconds;

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  private Claims parseClaimsFromToken(String token) {
    return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public String makeToken(Authentication authentication) {
    String username = authentication.getName();
    List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .toList();
    return Jwts.builder()
        .issuer(issuer)
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + (expirationTimeInSeconds * 1000)))
        .claim("username", username).claim("authorities", authorities)
        .signWith(getSecretKey()).compact();
  }

  public boolean validateToken(String token) {
    try {
      parseClaimsFromToken(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  public Authentication setAuthentication(String token) {
    Claims payload = parseClaimsFromToken(token);
    @SuppressWarnings("unchecked")
    List<String> authorities = payload.get("authorities", ArrayList.class);
    String username = payload.getSubject();

    List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
        .map(SimpleGrantedAuthority::new).toList();

    return new UsernamePasswordAuthenticationToken(username, "", grantedAuthorities);
  }
}
