package com.dashboard.api.infrastructure.jwt;

import org.springframework.http.ResponseCookie;

/**
 * Utility class for creating HTTP cookies related to JWT tokens.
 *
 * <p>Provides methods to generate cookies that store JWT tokens and methods to generate cookies
 * that effectively clear the token.
 */
public class JwtUtil {

  /**
   * Creates an HTTP-only cookie containing the given JWT token.
   *
   * @param token the JWT token to store in the cookie
   * @return a ResponseCookie configured with the token
   */
  public static ResponseCookie makeCookie(String token) {
    return ResponseCookie.from("TOKEN", token)
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(60 * 60) // 1 hour
        .sameSite("Lax")
        .build();
  }

  /**
   * Creates the string representation of an HTTP-only cookie containing the given JWT token.
   *
   * @param token the JWT token to store in the cookie
   * @return the Set-Cookie header string for the cookie
   */
  public static String makeCookieString(String token) {
    return makeCookie(token).toString();
  }

  /**
   * Creates an HTTP-only cookie that expires immediately, effectively clearing the JWT token.
   *
   * @return a ResponseCookie that clears the token cookie
   */
  public static ResponseCookie makeEmptyCookie() {
    return ResponseCookie.from("TOKEN", "")
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(0) // expires immediately
        .sameSite("Lax")
        .build();
  }

  /**
   * Creates the string representation of an HTTP-only cookie that expires immediately, effectively
   * clearing the JWT token.
   *
   * @return the Set-Cookie header string for the clearing cookie
   */
  public static String makeEmptyCookieString() {
    return makeEmptyCookie().toString();
  }
}
