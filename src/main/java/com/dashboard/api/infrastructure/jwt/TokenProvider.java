package com.dashboard.api.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

/**
 * Service responsible for creating, parsing, and validating JWT tokens.
 *
 * <p>Uses configured secret key, issuer, and expiration time to generate tokens and extract
 * authentication information.
 */
@Service
public class TokenProvider {

  @Value("${security.jwt.secret-key}")
  private String secret;

  @Value("${security.jwt.issuer}")
  private String issuer;

  @Value("${security.jwt.expiry-time-in-seconds}")
  private Long expirationTimeInSeconds;

  /**
   * Retrieves the secret key used for signing and verifying JWT tokens.
   *
   * @return the secret key
   */
  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Parses claims from a JWT token.
   *
   * @param token the JWT token string
   * @return the claims contained in the token
   */
  private Claims parseClaimsFromToken(String token) {
    return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Creates a JWT token for the given authenticated user.
   *
   * @param authentication the authentication object containing user details
   * @return the signed JWT token string
   */
  public String makeToken(Authentication authentication) {
    String username = authentication.getName();
    List<String> authorities =
        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return Jwts.builder()
        .issuer(issuer)
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + (expirationTimeInSeconds * 1000)))
        .claim("username", username)
        .claim("authorities", authorities)
        .signWith(getSecretKey())
        .compact();
  }

  /**
   * Validates the JWT token's integrity and expiration.
   *
   * @param token the JWT token string to validate
   * @return true if the token is valid, false otherwise
   */
  public boolean validateToken(String token) {
    try {
      parseClaimsFromToken(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  /**
   * Extracts authentication information from a valid JWT token.
   *
   * @param token the JWT token string
   * @return an Authentication object representing the authenticated user
   */
  public Authentication setAuthentication(String token) {
    Claims payload = parseClaimsFromToken(token);
    @SuppressWarnings("unchecked")
    List<String> authorities = payload.get("authorities", ArrayList.class);
    String username = payload.getSubject();

    List<SimpleGrantedAuthority> grantedAuthorities =
        authorities.stream().map(SimpleGrantedAuthority::new).toList();

    return new UsernamePasswordAuthenticationToken(username, "", grantedAuthorities);
  }
}
