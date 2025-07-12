package com.dashboard.api.application.controllers.user;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for user-related operations.
 *
 * <p>Provides endpoints for user authentication, registration, profile management, password
 * changes, and logout functionality. Access to sensitive endpoints is restricted to users with the
 * 'USER' role.
 */
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

  /**
   * Handles user login requests. Attempts to authenticate the user and issues a JWT token in a
   * secure HTTP-only cookie.
   *
   * @param request the login request payload containing username and password
   * @param response the HTTP response to add the authentication cookie to
   * @return HTTP 200 OK response on successful login
   * @throws UnauthorizedException if authentication fails
   */
  @PostMapping("/login")
  public ResponseEntity<Object> signIn(
      @RequestBody LoginRequest request, HttpServletResponse response) {
    try {
      String token = userService.login(request.username(), request.password());
      response.addHeader(HttpHeaders.SET_COOKIE, JwtUtil.makeCookieString(token));
      return ResponseEntity.ok().build();
    } catch (UnauthorizedException unauthorized) {
      throw unauthorized;
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(
      @RequestBody RegisterRequest user, HttpServletResponse response) {
    userService.register(user);
    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Registration Successful"));
  }

  @PutMapping("/update")
  @PreAuthorize("hasRole('USER')")
  public UserPresenter update(@RequestBody UpdateUserInput input) {
    return userService.update(input);
  }

  /**
   * Changes the username of the currently authenticated user.
   *
   * <p>If the username change is successful and a new JWT token is issued, the token is set in the
   * response cookie to update the client session.
   *
   * @param username the new username to be set
   * @param response the HTTP response to which a new cookie may be added
   * @return ResponseEntity containing the updated user information
   */
  @PutMapping("/updateUsername")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<UserPresenter> changeUsername(
      @RequestParam("username") String username, HttpServletResponse response) {
    UserWithTokenPresenter result = userService.changeUsername(username);
    if (result.token() != null) {
      response.addHeader(HttpHeaders.SET_COOKIE, JwtUtil.makeCookieString(result.token()));
    }

    return ResponseEntity.ok(result.user());
  }

  /**
   * Changes the password of the currently authenticated user.
   *
   * <p>On successful password change, the authentication cookie is cleared, forcing the user to
   * re-authenticate.
   *
   * @param password the new password to be set
   * @param response the HTTP response to which a cleared cookie is added
   * @return a success message string
   */
  @PutMapping("/changePassword")
  @PreAuthorize("hasRole('USER')")
  public String changePassword(@RequestBody String password, HttpServletResponse response) {
    userService.changePassword(password);
    response.addHeader("Set-Cookie", JwtUtil.makeEmptyCookieString());
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
  public ResponseEntity<String> logout(HttpServletResponse response) {
    response.addHeader("Set-Cookie", JwtUtil.makeEmptyCookieString());
    return ResponseEntity.ok("logged out");
  }
}
