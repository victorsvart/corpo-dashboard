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

  public static ResponseCookie MakeEmptyCookie() {
    return ResponseCookie.from("TOKEN", "")
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(0) // expires immediately
        .sameSite("Lax")
        .build();
  }

  public static String MakeEmptyCookieString() {
    return ResponseCookie.from("TOKEN", "")
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(0)
        .sameSite("Lax")
        .build()
        .toString();
  }
}
