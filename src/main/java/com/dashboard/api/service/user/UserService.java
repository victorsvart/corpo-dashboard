package com.dashboard.api.service.user;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dashboard.api.domain.user.User;
import com.dashboard.api.infrastructure.jwt.TokenProvider;
import com.dashboard.api.persistence.jpa.user.UserRepository;
import com.dashboard.api.service.user.dto.UpdateUserInput;
import com.dashboard.api.service.user.dto.UserPresenter;
import com.dashboard.api.service.user.dto.UserWithTokenPresenter;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public UserService(
      UserRepository userRepository,
      TokenProvider tokenProvider,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.tokenProvider = tokenProvider;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  private String getAuthenticatedUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated())
      throw new UnauthorizedException("User is not authenticated");

    Object principal = authentication.getPrincipal();
    if (principal instanceof String username)
      return username;

    throw new IllegalStateException("Unexpected authentication principal type: " + principal.getClass().getName());
  }

  private String remakeToken(User user) {
    List<SimpleGrantedAuthority> authorities = user.getAuthorities().stream()
        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
        .toList();

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user.getUsername(), null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return tokenProvider.makeToken(authentication);
  }

  public UserPresenter me() {
    String username = getAuthenticatedUsername();

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UnauthorizedException("User not found"));

    return UserPresenter.from(user);
  }

  public User register(User user) throws EntityExistsException {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new EntityExistsException("username is taken!");
    }

    if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
      user.setDefaultAuthority();
    }

    String hashedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);
    return userRepository.save(user);
  }

  public String login(String username, String password) {
    try {
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
          password);
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      return tokenProvider.makeToken(authentication);
    } catch (AuthenticationException ex) {
      ex.printStackTrace();
      throw new RuntimeException("Invalid username or password");
    }
  }

  public UserPresenter update(UpdateUserInput input) {
    String loggedUsername = getAuthenticatedUsername();
    User user = userRepository.findByUsername(loggedUsername)
        .orElseThrow(() -> new EntityNotFoundException("Username not found for authenticated user"));

    user.update(input.firstName(), input.lastName());
    user = userRepository.save(user);

    return UserPresenter.from(user);
  }

  public UserWithTokenPresenter changeUsername(String username) throws EntityNotFoundException {
    String loggedUsername = getAuthenticatedUsername();
    if (userRepository.existsByUsernameAndUsernameNot(username, loggedUsername)) {
      throw new EntityExistsException("Username is in use");
    }

    User user = userRepository.findByUsername(loggedUsername)
        .orElseThrow(() -> new EntityNotFoundException("Username not found for authenticated user"));

    boolean usernameChanged = !user.getUsername().equals(username);
    user.setUsername(username);
    user = userRepository.save(user);

    String token = usernameChanged ? remakeToken(user) : null;
    return new UserWithTokenPresenter(UserPresenter.from(user), token);

  }

  public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
      super(message);
    }
  }
}
