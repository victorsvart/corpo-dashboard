package com.dashboard.api.service.user;

import com.dashboard.api.domain.authority.Authority;
import com.dashboard.api.domain.exception.UnauthorizedException;
import com.dashboard.api.domain.user.User;
import com.dashboard.api.infrastructure.jwt.TokenProvider;
import com.dashboard.api.persistence.jpa.user.UserRepository;
import com.dashboard.api.service.base.session.UserSession;
import com.dashboard.api.service.user.dto.RegisterRequest;
import com.dashboard.api.service.user.dto.UpdateUserInput;
import com.dashboard.api.service.user.dto.UserPresenter;
import com.dashboard.api.service.user.dto.UserWithTokenPresenter;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class that handles user-related operations such as registration, login, profile updates,
 * and authentication token regeneration.
 *
 * <p>This service interacts with the UserRepository, TokenProvider, and Spring Security components
 * to perform its operations.
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserSession userSession;

  /**
   * Constructs a new {@code UserService} with required dependencies.
   *
   * @param userRepository the repository for accessing and persisting {@link User} entities
   * @param tokenProvider the provider responsible for generating and validating JWT tokens
   * @param passwordEncoder the encoder used to hash and verify user passwords
   * @param authenticationManager the Spring Security manager for performing authentication
   * @param userSession the session component for retrieving the currently authenticated user
   */
  public UserService(
      UserRepository userRepository,
      TokenProvider tokenProvider,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      UserSession userSession) {
    this.userRepository = userRepository;
    this.tokenProvider = tokenProvider;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.userSession = userSession;
  }

  /**
   * Generates a new JWT token for the given user based on their authorities.
   *
   * @param user the user whose token should be regenerated
   * @return a new JWT token string
   */
  private String remakeToken(User user) {
    List<SimpleGrantedAuthority> authorities =
        user.getAuthorities().stream()
            .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
            .toList();

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return tokenProvider.makeToken(authentication);
  }

  /**
   * Retrieves the currently authenticated user's data.
   *
   * @return a presenter containing the user's information
   */
  public UserPresenter me() {
    User user = userSession.getLoggedUserInfo();
    return UserPresenter.from(user);
  }

  /**
   * Registers a new user in the system.
   *
   * @param request the user registration request
   * @throws EntityExistsException if the username already exists
   */
  public void register(RegisterRequest request) throws EntityExistsException {
    boolean userNameTaken = userRepository.existsByUsername(request.username());

    if (userNameTaken) {
      throw new EntityExistsException("username is taken!");
    }

    Set<Authority> authorities =
        request.authorities() == null || request.authorities().isEmpty()
            ? Authority.defaultAuthority()
            : request.authorities();

    User user =
        new User.Builder()
            .username(request.username())
            .name(request.firstName())
            .lastName(request.lastName())
            .password(passwordEncoder.encode(request.password()))
            .profilePicture(
                "https://pm1.aminoapps.com/7258/5520799cf0539b408bd8abee0a14d3a492ee5107r1-753-753v2_hq.jpg")
            .authorities(authorities)
            .build();

    userRepository.save(user);
  }

  /**
   * Authenticates a user and generates a JWT token.
   *
   * @param username the user's username
   * @param password the user's password
   * @return a valid JWT token if authentication succeeds
   * @throws UnauthorizedException if authentication fails
   */
  public String login(String username, String password) {
    try {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(username, password);
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      return tokenProvider.makeToken(authentication);
    } catch (AuthenticationException ex) {
      ex.printStackTrace();
      throw new UnauthorizedException("Invalid username or password");
    }
  }

  /**
   * Updates the current user's name and last name.
   *
   * @param input object containing updated name and last name
   * @return a presenter representing the updated user
   */
  public UserPresenter update(UpdateUserInput input) {
    User user = userSession.getLoggedUserInfo();
    user.update(input.firstName(), input.lastName());
    user = userRepository.save(user);
    return UserPresenter.from(user);
  }

  /**
   * Changes the current user's username.
   *
   * @param username the new username
   * @return a presenter containing the updated user and a new JWT token if the username changed
   * @throws EntityNotFoundException if the user session is invalid
   */
  public UserWithTokenPresenter changeUsername(String username) throws EntityNotFoundException {
    User user = userSession.getLoggedUserInfo();
    boolean usernameChanged = !user.getUsername().equals(username);
    user.setUsername(username);
    user = userRepository.save(user);

    String token = usernameChanged ? remakeToken(user) : null;
    return new UserWithTokenPresenter(UserPresenter.from(user), token);
  }

  /**
   * Changes the current user's password.
   *
   * @param password the new raw password to be encoded and saved
   */
  public void changePassword(String password) {
    User user = userSession.getLoggedUserInfo();
    String newPassword = passwordEncoder.encode(password);
    user.setPassword(newPassword);
    userRepository.save(user);
  }

  /**
   * Updates the user's profile picture URL.
   *
   * @param picUrl the new profile picture URL
   */
  public void changeUserProfilePic(String picUrl) {
    User user = userSession.getLoggedUserInfo();
    user.setProfilePicture(picUrl);
    userRepository.save(user);
  }
}
