package com.dashboard.api.infrastructure.security;

import com.dashboard.api.domain.authority.Authority;
import com.dashboard.api.domain.user.User;
import com.dashboard.api.persistence.jpa.user.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 *
 * <p>Loads user details from the database using the UserRepository. Converts user's Authorities to
 * Spring Security SimpleGrantedAuthorities.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Constructs the service with the given UserRepository.
   *
   * @param userRepository the repository used to fetch user data
   */
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Maps the application's Authority entities to Spring Security's SimpleGrantedAuthority.
   *
   * @param authorities the set of Authority entities
   * @return a list of SimpleGrantedAuthority objects
   */
  private List<SimpleGrantedAuthority> mapAuthorityToSimpleGrantedAuthority(
      Set<Authority> authorities) {
    return authorities.stream()
        .map(Authority::getAuthority)
        .map(SimpleGrantedAuthority::new)
        .toList();
  }

  /**
   * Loads the user details by username.
   *
   * @param username the username identifying the user whose data is required.
   * @return a fully populated UserDetails object (never null)
   * @throws UsernameNotFoundException if the user could not be found or the user has no
   *     GrantedAuthority
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optUser = userRepository.findByUsername(username);
    if (optUser.isEmpty()) {
      throw new UsernameNotFoundException("User with username: " + username + " not found");
    }

    User user = optUser.get();
    String userName = user.getUsername();
    String password = user.getPassword();
    List<SimpleGrantedAuthority> grantedAuthorities =
        mapAuthorityToSimpleGrantedAuthority(user.getAuthorities());
    return new org.springframework.security.core.userdetails.User(
        userName, password, grantedAuthorities);
  }
}
