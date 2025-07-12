package com.dashboard.api.persistence.seed;

import com.dashboard.api.domain.authority.Authority;
import com.dashboard.api.domain.user.User;
import com.dashboard.api.persistence.jpa.authority.AuthorityRepository;
import com.dashboard.api.service.user.UserService;
import com.dashboard.api.service.user.dto.RegisterRequest;
import jakarta.persistence.EntityExistsException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Seeder component to initialize a default sample user in the database at application startup.
 *
 * <p>Implements CommandLineRunner to insert a predefined user if it does not already exist.
 * Retrieves the "ROLE_USER" authority from the repository and uses UserService for registration.
 * Handles duplicate user registration gracefully by catching EntityExistsException.
 */
@Component
@Order(3)
public class UserSeeder implements CommandLineRunner {
  private final UserService userService;
  private final AuthorityRepository authorityRepository;

  @Value("${seeding.user.username}")
  private String userUsername;

  @Value("${seeding.user.password}")
  private String userPassword;

  public UserSeeder(UserService userService, AuthorityRepository authorityRepository) {
    this.userService = userService;
    this.authorityRepository = authorityRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    String profilePic =
        "https://pm1.aminoapps.com/7258/5520799cf0539b408bd8abee0a14d3a492ee5107r1-753-753v2_hq.jpg";
    Set<Authority> role = Set.of(authorityRepository.findById("ROLE_USER").orElseThrow());
    User user = new User(userUsername, userPassword, "User", "Da Silva", profilePic, role);
    try {
      RegisterRequest request =
          new RegisterRequest(
              user.getUsername(),
              user.getPassword(),
              user.getName(),
              user.getLastName(),
              user.getAuthorities());
      userService.register(request);
    } catch (EntityExistsException e) {
      System.out.println("Sample user already seeded");
      return;
    }

    System.out.println("Seeded sample user");
  }
}
