package com.dashboard.api.application.controllers.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import com.dashboard.api.domain.exception.UnauthorizedException;
import com.dashboard.api.infrastructure.jwt.JwtUtil;
import com.dashboard.api.service.user.UserService;
import com.dashboard.api.service.user.dto.ChangeProfilePictureRequest;
import com.dashboard.api.service.user.dto.LoginRequest;
import com.dashboard.api.service.user.dto.RegisterRequest;
import com.dashboard.api.service.user.dto.UpdateUserInput;
import com.dashboard.api.service.user.dto.UserPresenter;
import com.dashboard.api.service.user.dto.UserWithTokenPresenter;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@EnableMethodSecurity
@RequestMapping("/user")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public UserPresenter me() {
    return userService.me();
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest request, HttpServletResponse response) {
    try {
      String token = userService.login(request.username(), request.password());
      response.addHeader(HttpHeaders.SET_COOKIE, JwtUtil.MakeCookieString(token));
      return ResponseEntity.ok().build();
    } catch (UnauthorizedException unauthorized) {
      throw unauthorized;
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody RegisterRequest user, HttpServletResponse response) {
    userService.register(user);
    return ResponseEntity.status(HttpStatus.OK)
        .body(Map.of("message", "Registration Successful"));
  }

  @PutMapping("/update")
  @PreAuthorize("hasRole('USER')")
  public UserPresenter update(@RequestBody UpdateUserInput input) {
    return userService.update(input);
  }

  @PutMapping("/updateUsername")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<UserPresenter> changeUsername(@RequestParam("username") String username,
      HttpServletResponse response) {
    UserWithTokenPresenter result = userService.changeUsername(username);
    if (result.token() != null)
      response.addHeader(HttpHeaders.SET_COOKIE, JwtUtil.MakeCookieString(result.token()));

    return ResponseEntity.ok(result.user());
  }

  @PutMapping("/changePassword")
  @PreAuthorize("hasRole('USER')")
  public String changePassword(@RequestBody String password,
      HttpServletResponse response) {
    userService.changePassword(password);
    response.addHeader("Set-Cookie", JwtUtil.MakeEmptyCookieString());
    return "Successful";
  }

  @PutMapping("/changeProfilePic")
  @PreAuthorize("hasRole('USER')")
  public String changeProfilePic(@RequestBody ChangeProfilePictureRequest request) {
    userService.changeUserProfilePic(request.profilePicture());
    return "Successful";
  }

  @GetMapping("/logout")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<String> logout(
      HttpServletResponse response) {
    response.addHeader("Set-Cookie", JwtUtil.MakeEmptyCookieString());
    return ResponseEntity.ok("logged out");
  }
}
