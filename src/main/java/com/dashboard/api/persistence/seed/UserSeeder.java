package com.dashboard.api.persistence.seed;

import com.dashboard.api.domain.authority.Authority;
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
    try {
      Set<Authority> role = Set.of(authorityRepository.findById("ROLE_USER").orElseThrow());
      RegisterRequest request =
          new RegisterRequest(userUsername, userPassword, "User", "Da Silva", role);
      userService.register(request);
    } catch (EntityExistsException e) {
      System.out.println("Sample user already seeded");
      return;
    }

    System.out.println("Seeded sample user");
  }
}
