package com.dashboard.api.persistence.seed;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.dashboard.api.domain.serverStatus.ProjectStatus;
import com.dashboard.api.persistence.jpa.serverStatus.ProjectStatusRepository;

@Component
@Order(4)
public class ProjectStatusSeed implements CommandLineRunner {

  private final ProjectStatusRepository projectStatusRepository;

  public ProjectStatusSeed(ProjectStatusRepository serverStatusRepository) {
    this.projectStatusRepository = serverStatusRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    final List<ProjectStatus> statuses = List.of(
        new ProjectStatus("HEALTHY"),
        new ProjectStatus("DEPLOYING"),
        new ProjectStatus("MAINTENANCE"),
        new ProjectStatus("DEPLOY ERROR"));

    for (ProjectStatus ss : statuses) {
      if (projectStatusRepository.existsByName(ss.getName()))
        return;

      projectStatusRepository.save(ss);
    }
  }
}
