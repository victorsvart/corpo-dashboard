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
 * Seeder component to create the default admin user at application startup.
 *
 * <p>This class implements CommandLineRunner to run after the application context is loaded. It
 * checks for the presence of admin roles and registers an admin user if not already present.
 */
@Component
@Order(2)
public class AdminUserSeeder implements CommandLineRunner {
  private final UserService userService;
  private final AuthorityRepository authorityRepository;

  @Value("${seeding.admin.username}")
  private String adminUsername;

  @Value("${seeding.admin.password}")
  private String adminPassword;

  public AdminUserSeeder(UserService userService, AuthorityRepository authorityRepository) {
    this.userService = userService;
    this.authorityRepository = authorityRepository;
  }

  @Override
  public void run(String... args) {
    Set<Authority> roles =
        Set.of(
            authorityRepository.findById("ROLE_USER").orElseThrow(),
            authorityRepository.findById("ROLE_ADMIN").orElseThrow());

    try {
      RegisterRequest request =
          new RegisterRequest(adminUsername, adminPassword, "admin", "admin", roles);
      userService.register(request);
    } catch (EntityExistsException e) {
      System.out.println("Admin user already seeded");
      return;
    }

    System.out.println("Seeded admin user");
  }
}
