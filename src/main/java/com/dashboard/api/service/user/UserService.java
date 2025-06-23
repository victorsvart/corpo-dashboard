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
import com.dashboard.api.service.base.session.UserSession;
import com.dashboard.api.service.mapper.Mapper;
import com.dashboard.api.service.user.dto.RegisterRequest;
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
  private final UserSession userSession;

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
    User user = userSession.getLoggedUserInfo();
    return UserPresenter.from(user);
  }

  public void register(RegisterRequest request) throws EntityExistsException {
    if (userRepository.existsByUsername(request.username())) {
      throw new EntityExistsException("username is taken!");
    }

    User user = Mapper.from(request);
    if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
      user.setDefaultAuthority();
    }

    String hashedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);
    userRepository.save(user);
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
    User user = userSession.getLoggedUserInfo();
    Mapper.fromTo(input, user);
    user = userRepository.save(user);

    return UserPresenter.from(user);
  }

  public UserWithTokenPresenter changeUsername(String username) throws EntityNotFoundException {
    User user = userSession.getLoggedUserInfo();
    boolean usernameChanged = !user.getUsername().equals(username);
    user.setUsername(username);
    user = userRepository.save(user);

    String token = usernameChanged ? remakeToken(user) : null;
    return new UserWithTokenPresenter(UserPresenter.from(user), token);

  }

  public void changePassword(String password) {
    User user = userSession.getLoggedUserInfo();
    String newPassword = passwordEncoder.encode(password);
    user.setPassword(newPassword);
    userRepository.save(user);
  }

  public void changeUserProfilePic(String picUrl) {
    User user = userSession.getLoggedUserInfo();
    user.setProfilePicture(picUrl);
    userRepository.save(user);
  }
}
