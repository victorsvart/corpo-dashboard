package com.dashboard.api.infrastructure.jwt;

import org.springframework.http.ResponseCookie;

public class JwtUtil {
  public static ResponseCookie MakeCookie(String token) {
    return ResponseCookie.from("TOKEN", token)
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(60 * 60)
        .sameSite("Lax")
        .build();
  }

  public static String MakeCookieString(String token) {
    return ResponseCookie.from("TOKEN", token)
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(60 * 60)
        .sameSite("Lax")
        .build()
        .toString();
  }
}
