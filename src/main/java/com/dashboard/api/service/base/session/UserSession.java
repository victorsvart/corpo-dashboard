package com.dashboard.api.service.base.session;

import com.dashboard.api.domain.user.User;
import com.dashboard.api.persistence.jpa.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

  private final UserRepository userRepository;

  public UserSession(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getLoggedUserInfo() {
    String username = getAuthenticatedUsername();
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalStateException("Authenticated user not found: " + username));
  }

  private String getAuthenticatedUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UnauthorizedException("User is not authenticated");
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof String username) {
      return username;
    }

    throw new IllegalStateException("Unexpected authentication principal type: " + principal.getClass().getName());
  }

  public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
      super(message);
    }
  }
}
