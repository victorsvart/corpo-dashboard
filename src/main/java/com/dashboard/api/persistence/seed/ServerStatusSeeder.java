package com.dashboard.api.persistence.seed;

import com.dashboard.api.domain.serverstatus.ServerStatus;
import com.dashboard.api.persistence.jpa.serverstatus.ServerStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** Seeder component to initialize default servers status in the database at application startup. */
@Component
@Order(6)
public class ServerStatusSeeder implements CommandLineRunner {

  private final ServerStatusRepository serverStatusRepository;

  public ServerStatusSeeder(ServerStatusRepository serverStatusRepository) {
    this.serverStatusRepository = serverStatusRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    for (String name : ServerStatus.ALL_STATUSES) {
      ServerStatus status = new ServerStatus(name);
      if (serverStatusRepository.existsByName(name)) {
        return;
      }

      serverStatusRepository.save(status);
    }
  }
}
