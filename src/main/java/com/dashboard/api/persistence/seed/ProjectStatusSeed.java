package com.dashboard.api.persistence.seed;

import com.dashboard.api.domain.projectstatus.ProjectStatus;
import com.dashboard.api.persistence.jpa.projectstatus.ProjectStatusRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Seeder component to initialize default project statuses in the database at startup.
 *
 * <p>Implements CommandLineRunner to run after application initialization. Inserts the default
 * statuses only if they do not already exist.
 */
@Component
@Order(4)
public class ProjectStatusSeed implements CommandLineRunner {

  private final ProjectStatusRepository projectStatusRepository;

  public ProjectStatusSeed(ProjectStatusRepository serverStatusRepository) {
    this.projectStatusRepository = serverStatusRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    final List<ProjectStatus> statuses =
        List.of(
            new ProjectStatus("HEALTHY"),
            new ProjectStatus("DEPLOYING"),
            new ProjectStatus("MAINTENANCE"),
            new ProjectStatus("DEPLOY ERROR"));

    for (ProjectStatus ss : statuses) {
      if (projectStatusRepository.existsByName(ss.getName())) {
        return;
      }

      if (ss.getName() == "HEALTHY") {
        ss.setAsDefault();
      }

      projectStatusRepository.save(ss);
    }
  }
}
