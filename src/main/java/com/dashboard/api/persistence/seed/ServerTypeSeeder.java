package com.dashboard.api.persistence.seed;

import com.dashboard.api.domain.servertype.ServerType;
import com.dashboard.api.persistence.jpa.servertypes.ServerTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Seeder component to initialize ServerTypes.
 *
 * <p>Implements CommandLineRunner to insert a predefined user if it does not already exist.
 * Retrieves the "ROLE_USER" authority from the repository and uses UserService for registration.
 * Handles duplicate user registration gracefully by catching EntityExistsException.
 */
@Component
@Order(7)
public class ServerTypeSeeder implements CommandLineRunner {
  private final ServerTypeRepository serverTypeRepository;

  public ServerTypeSeeder(ServerTypeRepository serverTypeRepository) {
    this.serverTypeRepository = serverTypeRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    for (String name : ServerType.ALL_TYPES) {
      ServerType type = new ServerType(name);
      if (serverTypeRepository.existsByName(name)) {
        return;
      }

      serverTypeRepository.save(type);
    }
  }
}
