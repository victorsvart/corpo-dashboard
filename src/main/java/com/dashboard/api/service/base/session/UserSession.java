package com.dashboard.api.service.base.session;

import com.dashboard.api.domain.user.User;
import com.dashboard.api.persistence.jpa.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Provides utility methods to access the current logged-in user's information based on Spring
 * Security's Authentication context.
 *
 * <p>Retrieves the username of the authenticated user from the SecurityContext, then loads the full
 * User entity from the UserRepository. Throws an exception if no authenticated user is present or
 * if user not found.
 */
@Component
public class UserSession {

  private final UserRepository userRepository;

  /**
   * Constructs the UserSession with the required UserRepository.
   *
   * @param userRepository repository used to fetch user details
   */
  public UserSession(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Returns the full User entity of the currently authenticated user.
   *
   * @return the User entity of the logged-in user
   * @throws IllegalStateException if the authenticated user is not found in the repository
   * @throws UnauthorizedException if no user is currently authenticated
   */
  public User getLoggedUserInfo() {
    String username = getAuthenticatedUsername();
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new IllegalStateException("Authenticated user not found: " + username));
  }

  /**
   * Returns the username of the currently authenticated user.
   *
   * @return username of the authenticated user
   * @throws UnauthorizedException if no user is authenticated
   * @throws IllegalStateException if the authentication principal type is unexpected
   */
  private String getAuthenticatedUsername() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UnauthorizedException("User is not authenticated");
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof String username) {
      return username;
    }

    throw new IllegalStateException(
        "Unexpected authentication principal type: " + principal.getClass().getName());
  }

  /** Exception thrown when no user is authenticated in the current security context. */
  public static class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
      super(message);
    }
  }
}
