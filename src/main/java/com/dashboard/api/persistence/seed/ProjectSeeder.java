package com.dashboard.api.persistence.seed;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.domain.projectstatus.ProjectStatus;
import com.dashboard.api.domain.server.Server;
import com.dashboard.api.persistence.jpa.project.ProjectRepository;
import com.dashboard.api.persistence.jpa.projectstatus.ProjectStatusRepository;
import com.dashboard.api.persistence.jpa.server.ServerRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Seeder component to initialize default projects in the database at application startup.
 *
 * <p>This class implements CommandLineRunner and seeds projects only if none exist. It requires
 * existing ProjectStatus and Server entries to associate with projects.
 */
@Component
@Order(9)
public class ProjectSeeder implements CommandLineRunner {
  private final ProjectRepository projectRepository;
  private final ProjectStatusRepository statusRepository;
  private final ServerRepository serverRepository;

  /**
   * Constructs a new ProjectSeeder with the given repositories.
   *
   * @param projectRepository the repository for managing projects
   * @param statusRepository the repository for managing project statuses
   * @param serverRepository the repository for managing servers
   */
  public ProjectSeeder(
      ProjectRepository projectRepository,
      ProjectStatusRepository statusRepository,
      ServerRepository serverRepository) {
    this.projectRepository = projectRepository;
    this.statusRepository = statusRepository;
    this.serverRepository = serverRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (projectRepository.count() > 0) {
      return;
    }

    ProjectStatus healthyStatus =
        statusRepository
            .findByName("HEALTHY")
            .orElseThrow(() -> new IllegalStateException("Missing status: HEALTHY"));

    ProjectStatus deployingStatus =
        statusRepository
            .findByName("DEPLOYING")
            .orElseThrow(() -> new IllegalStateException("Missing status: HEALTHY"));

    List<Server> allServers = serverRepository.findAll();
    List<Server> subset = allServers.size() > 1 ? allServers.subList(0, 2) : allServers;
    Project p1 = new Project("Alpha Project", subset);
    p1.setStatus(healthyStatus);
    p1.setDetails(
        "Alpha Project focuses on real-time analytics for financial markets. Designed for speed and accuracy, it processes millions of data points per second to help institutions make smarter trades.");

    Project p2 = new Project("Beta Project", subset);
    p2.setStatus(deployingStatus);
    p2.setDetails(
        "Beta Project is a next-generation deployment automation tool. It simplifies CI/CD workflows and enables teams to ship code faster and more reliably across multiple environments.");

    Project p3 = new Project("Gamma Project");
    p3.setStatus(healthyStatus);
    p3.setDetails(
        "Gamma Project is a standalone initiative to modernize legacy systems. Its goal is to re-architect critical infrastructure with scalable, cloud-native technologies.");

    projectRepository.saveAll(List.of(p1, p2, p3));
  }
}
